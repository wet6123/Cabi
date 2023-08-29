package org.ftclub.cabinet.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.ftclub.cabinet.exception.ExceptionStatus;
import org.ftclub.cabinet.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class TicketingSharedCabinet {

	private static final Integer MAX_SHARE_CODE_TRY = 3;
	private final HashOperations<Long, Long, Integer> valueHashOperations;
	private final ValueOperations<Long, Long> valueOperations;
	private final RedisTemplate<Long, Integer> shadowKeyRedisTemplate;

	@Autowired
	public TicketingSharedCabinet(RedisTemplate<Long, Long> valueRedisTemplate,
			RedisTemplate<Long, Integer> shadowKeyRedisTemplate) {
		this.valueOperations = valueRedisTemplate.opsForValue();
		this.valueHashOperations = valueRedisTemplate.opsForHash();
		this.shadowKeyRedisTemplate = shadowKeyRedisTemplate;
	}

//	/**
//	 * @param key     : cabinetId + suffix
//	 * @param hashKey : ${userName} 또는 "userCount"
//	 */
//	public void saveValue(Long key, Long hashKey) {
//		if (isUserInValueKey(key, hashKey)) {
//			valueHashOperations.put(key, hashKey, getValue(key, hashKey) + 1);
//		} else {
//			if (getSizeOfUsers(key) == 0) // (최초에) 방장이 들어온 경우
//			{
//				valueHashOperations.put(key, hashKey, -1);
//			} else // 방장 이후 유저가 들어올려고 시도한 경우
//			{
//				valueHashOperations.put(key, hashKey, 1);
//			}
//		}
//	}

	/**
	 * @param key          : cabinetId
	 * @param hashKey      : userId
	 * @param shareCode    : 초대코드
	 * @param hasShadowKey : 최초 대여인지 아닌지 여부
	 */
	public void saveValue(Long key, Long hashKey, Integer shareCode, boolean hasShadowKey) {
		if (!hasShadowKey || isValidShareCode(key, shareCode)) { // 방장이거나 초대코드를 맞게 입력한 경우
			valueHashOperations.put(key, hashKey, -1);    // userId를 hashKey로 하여 -1을 value로 저장
			valueOperations.set(hashKey, key);    // userId를 key로 하여 cabinetId를 value로 저장
		} else { // 초대코드가 틀린 경우
			int trialCount =
					valueHashOperations.get(key, hashKey) != null ? getValue(key, hashKey) : 0;
			valueHashOperations.put(key, hashKey, trialCount + 1);    // trialCount를 1 증가시켜서 저장
			throw new ServiceException(ExceptionStatus.WRONG_SHARE_CODE);
		}
	}

	public boolean isValidShareCode(Long key, Integer shareCode) {
		return Objects.equals(shadowKeyRedisTemplate.opsForValue().get(key), shareCode);
	}

	public boolean checkPwTrialCount(Long key, Long hashKey) {
		return Objects.equals(valueHashOperations.get(key, hashKey), MAX_SHARE_CODE_TRY);
	}

	public Boolean isUserInValueKey(Long key, Long hashKey) {
		return valueHashOperations.hasKey(key, hashKey);
	}

	public Long getSizeOfUsers(Long key) {
		Map<Long, Integer> entries = valueHashOperations.entries(key);
		return entries.values().stream().filter(Objects::nonNull).filter(value -> value.equals(-1))
				.count();
	}

	public ArrayList<Long> getUserIdList(Long key) {
		Map<Long, Integer> entries = valueHashOperations.entries(key);
		if (entries == null) {
			return new ArrayList<>();
		}
		return entries.entrySet().stream().filter(entry -> entry.getValue().equals(-1)).map(
				Map.Entry::getKey).collect(Collectors.toCollection(ArrayList::new));
	}

	public Integer getValue(Long key, Long hashKey) {
		return valueHashOperations.get(key, hashKey);
	}

	public Integer getShareCode(Long key) {
		return shadowKeyRedisTemplate.opsForValue().get(key);
	}

	public void setShadowKey(Long cabinetId) {
		Random rand = new Random();
		int shareCode = 1000 + rand.nextInt(9000);
		shadowKeyRedisTemplate.opsForValue().set(cabinetId, shareCode);
		// 해당 키가 처음 생성된 것이라면 timeToLive 설정
		System.out.println("set expire time");
		shadowKeyRedisTemplate.expire(cabinetId, 5, TimeUnit.SECONDS);
	}

	public Boolean isShadowKey(Long cabinetId) {
		// 해당 키가 존재하는지 확인
		return shadowKeyRedisTemplate.hasKey(cabinetId);
	}

	public void deleteShadowKey(Long cabinetId) {
		shadowKeyRedisTemplate.delete(cabinetId);
	}

	public void deleteUserInValueKey(Long key, Long hashKey) { // user를 지우는 delete
		valueHashOperations.delete(key, hashKey);
		valueOperations.getOperations().delete(hashKey);
	}

	public void deleteValueKey(Long key) {
		valueOperations.getOperations().delete(key);
	}

	public Long findCabinetIdByUserId(Long userId) {
//		try {
//			return valueOperations.get(userId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return valueOperations.get(userId);

//		return null;
	}

	public List<Long> getUserIdsByCabinetId(Long cabinetId) {
		return getUserIdList(cabinetId);
	}

	public Long getSessionExpiredAt(Long cabinetId) {
		return shadowKeyRedisTemplate.getExpire(cabinetId, TimeUnit.SECONDS);
	}
}

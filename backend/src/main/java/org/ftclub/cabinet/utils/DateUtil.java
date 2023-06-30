package org.ftclub.cabinet.utils;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

/**
 * Date의 계산을 도와주기 위한 static 메서드를 가지고 있는 class
 */
public class DateUtil {

	private static final LocalDateTime INFINITY_DATE = stringToDate("9999-12-31");

	/**
	 * 아주 먼 미래의 날을 리턴합니다. 적당히 무한을 나타내는 값으로 사용할 수 있습니다.
	 *
	 * @return 9999-12-13에 해당하는 날을 리턴합니다.
	 */
	public static LocalDateTime getInfinityDate() {
		return INFINITY_DATE;
	}

	/**
	 * 문자열을 받아 그 문자열에 맞는 {@link LocalDateTime}를 리턴합니다.
	 *
	 * @param str yyyy-mm-dd 혹은 yyyy-m-dd 혹은 yyyy-m-d 포멧인 {@link String}
	 * @return 문자열 포멧에 맞는 {@link LocalDateTime}
	 * @throws RuntimeException 문자열 포멧이 적절하지 않다면 발생시킵니다.
	 */
	public static LocalDateTime stringToDate(String str) {
		boolean matches = Pattern.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$",
				str);
		if (!matches) {
			throw new RuntimeException("적절하지 않은 날짜 포맷의 String 입니다.");
		}
		return LocalDateTime.from(LocalDate.parse(str).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 특정 date에서 days만큼 더한 값을 리턴합니다.
	 *
	 * @param str  yyyy-mm-dd 혹은 yyyy-m-dd 혹은 yyyy-m-d 포멧인 {@link String}
	 * @param days 더하고 싶은 일 수
	 * @return str 포멧에 맞는 date에 days만큼 더한 값을 리턴합니다.
	 * @throws RuntimeException 문자열 포멧이 적절하지 않다면 발생시킵니다.
	 */
	public static LocalDateTime stringToDateAndAddDays(String str, int days) {
		LocalDateTime date = stringToDate(str);
		return date.plusDays(days);
	}

	/**
	 * 두 date의 차이만큼의 day를 절대값으로 리턴합니다.
	 *
	 * @param day1 day1
	 * @param day2 day2
	 * @return 두 day 차이의 절댓값
	 */
	public static Long calculateTwoDateDiffAbs(LocalDateTime day1, LocalDateTime day2) {
		return Math.abs(Duration.between(day1, day2).toNanos());
	}

	/**
	 * 두 date의 차이만큼의 day를 리턴합니다.
	 *
	 * @param day1 day1
	 * @param day2 day2
	 * @return day1 - day2
	 */
	public static Long calculateTwoDateDiff(LocalDateTime day1, LocalDateTime day2) {
		return Duration.between(day1, day2).toNanos();
	}
}
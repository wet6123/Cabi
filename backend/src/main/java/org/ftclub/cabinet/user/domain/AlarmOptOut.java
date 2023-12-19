package org.ftclub.cabinet.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ftclub.cabinet.alarm.domain.AlarmType;
import org.ftclub.cabinet.exception.DomainException;
import org.ftclub.cabinet.exception.ExceptionStatus;
import org.ftclub.cabinet.utils.ExceptionUtil;

/**
 * 유저의 알람 수신 거부 정보 엔티티입니다.
 */
@Entity
@Table(name = "ALARM_OPT_OUT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"user"})
public class AlarmOptOut {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "ALARM_TYPE", length = 32)
	private AlarmType alarmType;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	private AlarmOptOut(User user, AlarmType alarmType) {
		this.user = user;
		this.alarmType = alarmType;
	}

	public static AlarmOptOut of(User user, AlarmType alarmType) {
		AlarmOptOut alarmOptOut = new AlarmOptOut(user, alarmType);
		ExceptionUtil.throwIfFalse(alarmOptOut.isValid(),
				new DomainException(ExceptionStatus.INVALID_ARGUMENT));
		return alarmOptOut;
	}

	private boolean isValid() {
		return user != null && alarmType != null;
	}
}

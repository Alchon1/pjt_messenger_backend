package org.zerock.myapp.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SuppressWarnings("unused")
public class DateTimeUtils {

	
	public static String formatDate(Date date) {
		log.info("formatDate({}) invoked.", date);
		
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
	}

	// Create a Calendar object with the specified year, month, date, hour, minute, second
	// Where, month is started with 0 (January)
	public static Calendar getCalendar(int year, int month, int date, int hour, int minute, int second) {
		log.info("getCalendar({}, {}, {}, {}, {}, {}) invoked.", year, month, date, hour, minute, second);
		
		Calendar calendar = Calendar.getInstance();
//		Calendar calendar = Calendar.getInstance(Locale.KOREA);
//		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		
		calendar.set(year, month-1, date, hour, minute, second);
		
		return calendar;
	}
	
	// Create a LocalDateTime with the specified year, month, date, hour, minute, second
	// Where, month is started with 1 (January)
	public static LocalDateTime getLocalDateTime(int year, int month, int date, int hour, int minute, int second) {
		log.info("getLocalDateTime({}, {}, {}, {}, {}, {}) invoked.", year, month, date, hour, minute, second);
		
		return LocalDateTime.of(year, month, date, hour, minute, second);
	}
	
	// Create a LocalDate with the specified year, month, date
	// where, month is started with 1 (January)
	public static LocalDate getLocalDate(int year, int month, int date) {
		log.info("getLocalDate({}, {}, {}) invoked.", year, month, date);
		
		return LocalDate.of(year, month, date);
	}
	
	// Create a LocalTime with the specified hour, minute, second
	public static LocalTime getLocalTime(int hour, int minute, int second) {
		log.info("getLocalTime({}, {}, {}) invoked.", hour, minute, second);
		
		return LocalTime.of(hour, minute, second);
	}
	
	// 1. Create a Date object with the specified year, month, date, hour, minute, second.
	public static Date getDate1(int year, int month, int date, int hour, int minute, int second) {	// Ex: 2025, 3, 6, 8, 9, 17
		log.info("getDate1({}, {}, {}, {}, {}, {}) invoked.", year, month, date, hour, minute, second);
		
		return DateTimeUtils.getCalendar(year, month, date, hour, minute, second).getTime();
	}
	
	// 2. Create a Date object with the specified year, month, date, hour, minute, second Using java.sql.Timestamp.
	public static Date getDate2(String specifiedDateTime) {	// Ex: 2025-03-06 08:02:33
		log.info("getDate2({}) invoked.", specifiedDateTime);
		
		Timestamp timestamp = Timestamp.valueOf(specifiedDateTime);
		
		return new Date(timestamp.getTime());
	}
	
	// 3. Create a Date object with the specified year, month, date, hour, minute, second Using java.time.LocalDateTime.
	public static Date getDate3(int year, int month, int date, int hour, int minute, int second) {	// Ex: 2025, 3, 6, 8, 9, 17
		log.info("getDate3({}, {}, {}, {}, {}, {}) invoked.", year, month, date, hour, minute, second);
		
		LocalDateTime localDateTime = DateTimeUtils.getLocalDateTime(year, month, date, hour, minute, second);
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		
		return new Date(timestamp.getTime());
	}

	// 4. Create a Date object with the specified year, month, date, hour, minute, second Using java.time.LocalDateTime.
	public static Date getDate4(int year, int month, int date, int hour, int minute, int second) {	// Ex: 2025, 3, 6, 8, 9, 17
		log.info("getDate4({}, {}, {}, {}, {}, {}) invoked.", year, month, date, hour, minute, second);
		
		LocalDateTime localDateTime = DateTimeUtils.getLocalDateTime(year, month, date, hour, minute, second);
		
//		return Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());		// OK
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());		// OK
	}
	
	// 5. Create a Date object with the specified year, month, date, hour, minute, second Using java.time.LocalDate and LocalTime.
	public static Date getDate5(int year, int month, int date, int hour, int minute, int second) {	// Ex: 2025, 3, 6, 8, 9, 17
		log.info("getDate5({}, {}, {}, {}, {}, {}) invoked.", year, month, date, hour, minute, second);
		
		LocalDate localDate = DateTimeUtils.getLocalDate(year, month, date);
		LocalTime localTime = DateTimeUtils.getLocalTime(hour, minute, second);
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		
//		return Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());		// OK
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());		// OK
	}
	
	public static Long getDday(LocalDate targetDate) {
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(targetDate, today);
	}
	
	
/**	
	public static void main(String[] args) {
		log.info("main({}) invoked.", Arrays.toString(args));
		
		// -- 1 -------------
		Date date1 = DateTimeUtils.getDate1(2025, 2, 6, 7, 45, 7);
		log.info("1. date1: {}", DateTimeUtils.formatDate(date1));

		// -- 2 -------------
		Date date2 = DateTimeUtils.getDate2("1991-03-01 09:00:13");
		log.info("2. date2: {}", DateTimeUtils.formatDate(date2));

		// -- 3 -------------
		Date date3 = DateTimeUtils.getDate3(2025, 3, 6, 14, 30, 45);
		log.info("3. date3: {}", DateTimeUtils.formatDate(date3));
		
		// -- 4 -------------
		Date date4 = DateTimeUtils.getDate4(2025, 3, 6, 14, 30, 45);
		log.info("4. date4: {}", DateTimeUtils.formatDate(date4));
		
		// -- 5 -------------
		Date date5 = DateTimeUtils.getDate5(2025, 3, 6, 14, 30, 45);
		log.info("5. date5: {}", DateTimeUtils.formatDate(date5));		
	}
*/

} // end class

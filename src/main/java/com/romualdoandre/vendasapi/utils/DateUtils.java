package com.romualdoandre.vendasapi.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {

	public static Date fromString(String dateString, boolean endOfDay) {
		if(StringUtils.hasText(dateString)) {
			var data = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			Instant instant;
			if(endOfDay) {
				instant = data.atTime(LocalTime.of(23, 59)).atZone(ZoneId.systemDefault()).toInstant();
			}
			else {
				 instant = data.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			}
			return Date.from(instant);
		}
		return null;
	}
}

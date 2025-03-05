package com.romualdoandre.vendasapi.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {
	
	public static final Date DATA_INICIO_PADRAO;
	
	public static final String PADRAO_FORMATACAO_DATA= "dd/MM/yyyy";
	
	static {
		DATA_INICIO_PADRAO = DateUtils.fromString("01/01/1970", false);
	}

	public static Date fromString(String dateString, boolean endOfDay) {
		if(StringUtils.hasText(dateString)) {
			var data = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(PADRAO_FORMATACAO_DATA));
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
	
	public static Date hoje(boolean endOfDay) {
		String dataHojeString = LocalDate.now().format(DateTimeFormatter.ofPattern(PADRAO_FORMATACAO_DATA));
		return fromString(dataHojeString, endOfDay);
	}
}

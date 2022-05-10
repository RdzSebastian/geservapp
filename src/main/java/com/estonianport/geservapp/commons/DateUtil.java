package com.estonianport.geservapp.commons;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

	private static final String dateSeparatorFormatter = "-";

	private static final String dayFormatter = "dd";

	private static final String monthFormatter = "MM";

	private static final String yearFormatter = "yyyy";

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dayFormatter + dateSeparatorFormatter + monthFormatter + dateSeparatorFormatter + yearFormatter);

	private static final String timeSeparatorFormatter = ":";

	private static final String hourFormatter = "HH";

	private static final String minuteFormatter = "mm";

	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(hourFormatter + timeSeparatorFormatter + minuteFormatter);

	private static final String spaceSeparatorFormatter = " ";

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dayFormatter + dateSeparatorFormatter + monthFormatter + dateSeparatorFormatter + yearFormatter + spaceSeparatorFormatter + hourFormatter + timeSeparatorFormatter + minuteFormatter);

	private static final DateTimeFormatter dateTimeFormatterInverted = DateTimeFormatter.ofPattern(yearFormatter + dateSeparatorFormatter + monthFormatter + dateSeparatorFormatter + dayFormatter + spaceSeparatorFormatter + hourFormatter + timeSeparatorFormatter + minuteFormatter);

	public static final List<String> horas = new ArrayList<>(List.of("00", "01","02" ,"03" ,"04", "05","06", "07", "08", "09", "10", "11","12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"));

	public static final List<String> minutos = new ArrayList<>(List.of("00", "30"));

	public static String getFecha(LocalDateTime fecha) {
		return fecha.format(DateUtil.dateFormatter);
	}

	public static String getHora(LocalDateTime hora) {
		return hora.format(DateUtil.timeFormatter);
	}

	public static LocalDateTime createFechaConHora(String fecha) {
		return LocalDateTime.parse(fecha, DateUtil.dateTimeFormatter);
	}

	public static LocalDateTime createFechaInvertidaConHora(String fecha) {
		return LocalDateTime.parse(fecha, DateUtil.dateTimeFormatterInverted);
	}

}

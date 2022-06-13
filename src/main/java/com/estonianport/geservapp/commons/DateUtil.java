package com.estonianport.geservapp.commons;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class DateUtil {

	private static final String DATE_SEPARATOR = "-";

	private static final String DATE_SEPARATOR_SLASH = "/";

	private static final String DAY = "dd";

	private static final String MONTH = "MM";

	private static final String YEAR = "yyyy";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DAY + DATE_SEPARATOR + MONTH + DATE_SEPARATOR + YEAR);

	private static final String TIME_SEPARATOR = ":";

	private static final String HOUR = "HH";

	private static final String MINUTE = "mm";

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(HOUR + TIME_SEPARATOR + MINUTE);

	private static final String SPACE_SEPARATOR = " ";

	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DAY + DATE_SEPARATOR + MONTH + DATE_SEPARATOR + YEAR + SPACE_SEPARATOR + HOUR + TIME_SEPARATOR + MINUTE);

	private static final DateTimeFormatter DATETIME_FORMATTER_SLASH = DateTimeFormatter.ofPattern(DAY + DATE_SEPARATOR_SLASH + MONTH + DATE_SEPARATOR_SLASH + YEAR + SPACE_SEPARATOR + HOUR + TIME_SEPARATOR + MINUTE);

	private static final DateTimeFormatter DATETIME_FORMATTER_INVERTED = DateTimeFormatter.ofPattern(YEAR + DATE_SEPARATOR + MONTH + DATE_SEPARATOR + DAY + SPACE_SEPARATOR + HOUR + TIME_SEPARATOR + MINUTE);

	public static final List<String> HORAS = new ArrayList<>(List.of("00", "01","02" ,"03" ,"04", "05","06", "07", "08", "09", "10", "11","12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"));

	public static final List<String> MINUTOS = new ArrayList<>(List.of("00", "30"));

	public static final List<String> MESES = new ArrayList<>(List.of("Enero", "Febrero","Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"));

	public static final String START_TIME = "00:00";

	public static final String END_TIME = "23:59";

	static Set<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

	public static String getFecha(LocalDateTime fecha) {
		return fecha.format(DateUtil.DATE_FORMATTER);
	}

	public static String getHora(LocalDateTime hora) {
		return hora.format(DateUtil.TIME_FORMATTER);
	}

	public static String getFechaConHora(LocalDateTime fechaConHora) {
		return fechaConHora.format(DateUtil.DATETIME_FORMATTER);
	}

	public static LocalDateTime createFechaConHora(String fechaConHora) {
		return LocalDateTime.parse(fechaConHora, DateUtil.DATETIME_FORMATTER);
	}

	public static LocalDateTime createFechaConHora(String fecha, String hora) {
		return LocalDateTime.parse(fecha + SPACE_SEPARATOR + hora, DateUtil.DATETIME_FORMATTER);
	}

	public static LocalDateTime createFechaConHoraSlash(String fecha, String hora) {
		return LocalDateTime.parse(fecha + SPACE_SEPARATOR + hora, DateUtil.DATETIME_FORMATTER_SLASH);
	}

	public static LocalDateTime createFechaInvertidaConHora(String fechaConHora) {
		return LocalDateTime.parse(fechaConHora, DateUtil.DATETIME_FORMATTER_INVERTED);
	}

	public static LocalDateTime createFechaInvertidaConHora(String fecha, String hora) {
		return LocalDateTime.parse(fecha + SPACE_SEPARATOR + hora, DateUtil.DATETIME_FORMATTER_INVERTED);
	}

	public static boolean isFinDeSemana(LocalDateTime dateTime) {        
		return WEEKEND.contains(dateTime.getDayOfWeek());
	}

}

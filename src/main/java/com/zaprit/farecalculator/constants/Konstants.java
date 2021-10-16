package com.zaprit.farecalculator.constants;

import java.time.format.DateTimeFormatter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Konstants
{
	public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static final String GLOBAL_LOGGER_NAME = "FARE_LOGGER";
}

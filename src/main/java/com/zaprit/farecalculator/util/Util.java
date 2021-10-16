/**
 * 
 */
package com.zaprit.farecalculator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.zaprit.farecalculator.exception.CalculatorException;

import lombok.experimental.UtilityClass;

/**
 * @author vaibhav.singh
 */
@UtilityClass
public class Util
{
	public static int getWeekOfYear(LocalDate localDate)
	{
		//new Locale("en", "UK")
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public List<String> readFile(File inputFile) throws CalculatorException
	{
		var list = new ArrayList<String>();
		try (BufferedReader bufferReader = new BufferedReader(new FileReader(inputFile)))
		{
			String input = null;
			while ((input = bufferReader.readLine()) != null)
				list.add(input.trim());
		}
		catch (Exception e)
		{
			throw new CalculatorException(CalculatorException.INVALID_FILE, e);
		}
		return list;
	}
}

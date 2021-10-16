/**
 * 
 */
package com.zaprit.farecalculator.model.validator;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author vaibhav.singh
 */
public class TimeConstraintValidator implements ConstraintValidator<Time, String>
{
	@Override
	public boolean isValid(String inputTime, ConstraintValidatorContext context)
	{
		context.disableDefaultConstraintViolation();
		var valid = false;
		try
		{
			String[] data = inputTime.split(":");
			int size = data.length;
			if (size == 2)
				valid = LocalTime.of(Integer.parseInt(data[0]), Integer.parseInt(data[1])) != null;
			else if (size == 3)
				valid = LocalTime.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])) != null;
		}
		catch (Exception e)
		{}
		return valid;
	}
}

/**
 * 
 */
package com.zaprit.farecalculator.model.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author vaibhav.singh
 */
public class DateConstraintValidator implements ConstraintValidator<Date, String>
{
	@Override
	public boolean isValid(String validDate, ConstraintValidatorContext context)
	{
		context.disableDefaultConstraintViolation();
		var valid = false;
		try
		{
			// dd-MM-yyyy
			String[] splitDate = validDate.split("-");
			valid = LocalDate.of(Integer.valueOf(splitDate[2]), Integer.valueOf(splitDate[1]), Integer.valueOf(splitDate[0])) != null;
		}
		catch (Exception e)
		{}
		return valid;
	}
}

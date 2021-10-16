/**
 * 
 */
package com.zaprit.farecalculator.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author vaibhav.singh
 */
public class ZoneConstraintValidator implements ConstraintValidator<Zone, Integer>
{
	@Override
	public boolean isValid(Integer zone, ConstraintValidatorContext context)
	{
		context.disableDefaultConstraintViolation();
		var valid = false;
		try
		{
			valid = zone.intValue() == 1 || zone.intValue() == 2;
		}
		catch (Exception e)
		{}
		return valid;
	}
}

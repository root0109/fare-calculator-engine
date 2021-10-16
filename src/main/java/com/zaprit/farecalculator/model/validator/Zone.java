/**
 * 
 */
package com.zaprit.farecalculator.model.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author vaibhav.singh
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, LOCAL_VARIABLE })
@Constraint(validatedBy = ZoneConstraintValidator.class)
public @interface Zone
{
	String message() default "Zone is not supported";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

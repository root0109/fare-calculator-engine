/**
 * 
 */
package com.zaprit.farecalculator.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.zaprit.farecalculator.exception.CalculatorException;
import com.zaprit.farecalculator.model.Commute;
import com.zaprit.farecalculator.model.Commute.CommuteBuilder;
import com.zaprit.farecalculator.service.CalculationService;

/**
 * @author vaibhav.singh
 */
public final class FareProcessor
{
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private CalculationService calculationService = null;

	public FareProcessor(CalculationService service)
	{
		this.calculationService = service;
	}

	public double execute(List<String> inputs) throws CalculatorException
	{
		List<Commute> commuteObjList = new ArrayList<Commute>();
		inputs.forEach(input -> {
			var obj = validate(input);
			if (obj != null)
				commuteObjList.add(obj);
			else
				logger.log(Level.SEVERE, "Incorrect Input : " + input);

		});
		return calculationService.calculate(commuteObjList);
	}

	/*
	 * this should be a seperate utility if you want this public
	 */
	private Commute validate(String inputString)
	{
		String message = null;
		Commute commute = null;
		try
		{
			String[] inputs = inputString.split(" ");

			CommuteBuilder builder = new Commute.CommuteBuilder(inputs[0].trim(), inputs[1].trim(), Integer.parseInt(inputs[2].trim()),
			                Integer.parseInt(inputs[3].trim()));
			if (inputs.length == 5)
				builder.setAge(Integer.parseInt(inputs[4].trim())); // fifth optional input is age

			commute = builder.build();
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Commute>> violations = validator.validate(commute);
			for (ConstraintViolation<Commute> violation : violations)
			{
				message += violation.getMessage();
			}
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, "Ignoring Incorrect Input : " + inputString + " exception Msg: " + message == null ? e.getMessage() : message);
		}
		return commute;
	}
}

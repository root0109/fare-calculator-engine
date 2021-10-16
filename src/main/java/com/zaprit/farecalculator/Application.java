/**
 * 
 */
package com.zaprit.farecalculator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zaprit.farecalculator.exception.CalculatorException;
import com.zaprit.farecalculator.processor.FareProcessor;
import com.zaprit.farecalculator.service.impl.CalculationServiceImpl;
import com.zaprit.farecalculator.strategy.DailyCappedFareStrategy;
import com.zaprit.farecalculator.strategy.TimeBasedFareStrategy;
import com.zaprit.farecalculator.strategy.WeeklyCappedFareStrategy;
import com.zaprit.farecalculator.util.Util;

/**
 * Starting point of the application
 * @author vaibhav.singh
 */
public class Application
{
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * @param args
	 * @throws CalculatorException
	 */
	public static void main(String[] args) throws CalculatorException
	{
		logger.setLevel(Level.SEVERE);
		var weekStrategy = new WeeklyCappedFareStrategy();
		var dailyStrategy = new DailyCappedFareStrategy();
		var timeStrategy = new TimeBasedFareStrategy();
		//create strategy chain
		dailyStrategy.setNextFareStrategy(weekStrategy);
		timeStrategy.setNextFareStrategy(dailyStrategy);

		FareProcessor processor = new FareProcessor(new CalculationServiceImpl(timeStrategy));
		logger.log(Level.SEVERE, "-------------------------------------------------------------------");
		logger.log(Level.SEVERE, "------------------<< FARE CALCULATION ENGINE >>--------------------");
		logger.log(Level.SEVERE, "-------------------------------------------------------------------");
		File inputFile = new File(args[0]);
		try
		{
			logger.log(Level.SEVERE, "-------------------------------------------------------------------");
			logger.log(Level.SEVERE, "Output : " + processor.execute(Util.readFile(inputFile)));
			logger.log(Level.SEVERE, "-------------------------------------------------------------------");
		}
		catch (Exception e)
		{
			throw new CalculatorException(CalculatorException.INVALID_FILE, e);
		}
	}
}

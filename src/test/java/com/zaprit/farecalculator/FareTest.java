/**
 * 
 */
package com.zaprit.farecalculator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.zaprit.farecalculator.exception.CalculatorException;
import com.zaprit.farecalculator.processor.FareProcessor;
import com.zaprit.farecalculator.service.impl.CalculationServiceImpl;
import com.zaprit.farecalculator.strategy.DailyCappedFareStrategy;
import com.zaprit.farecalculator.strategy.DiscountBasedFareStrategy;
import com.zaprit.farecalculator.strategy.TimeBasedFareStrategy;
import com.zaprit.farecalculator.strategy.WeeklyCappedFareStrategy;
import com.zaprit.farecalculator.util.Util;

/**
 * @author vaibhav.singh
 */
public class FareTest
{
	private final String TEST_DAILY_INPUT_FILE    = "test_daily.txt";
	private final String TEST_WEEKLY_INPUT_FILE   = "test_weekly.txt";
	private final String TEST_DISCOUNT_INPUT_FILE = "test_discount.txt";
	private final String TEST_EMPTY_INPUT_FILE    = "test_empty.txt";
	private final String TEST_GARBAGE_INPUT_FILE  = "test_garbage.txt";

	@Test
	public void testEmptyFileFare() throws CalculatorException
	{
		var weekStrategy = new WeeklyCappedFareStrategy();
		var dailyStrategy = new DailyCappedFareStrategy();
		var timeStrategy = new TimeBasedFareStrategy();
		//create strategy chain
		dailyStrategy.setNextFareStrategy(weekStrategy);
		timeStrategy.setNextFareStrategy(dailyStrategy);

		FareProcessor processor = new FareProcessor(new CalculationServiceImpl(timeStrategy));
		File inputFile = new File(getClass().getClassLoader().getResource(TEST_EMPTY_INPUT_FILE).getFile());

		CalculatorException calculatorException = assertThrows(CalculatorException.class, () -> {
			var data = Util.readFile(inputFile);
			processor.execute(data);
		}, "Error file");

		assertTrue(calculatorException.getMessage().contains(CalculatorException.INVALID_FILE));
	}

	@Test
	public void testGarbageFileFare() throws CalculatorException
	{
		var weekStrategy = new WeeklyCappedFareStrategy();
		var dailyStrategy = new DailyCappedFareStrategy();
		var timeStrategy = new TimeBasedFareStrategy();
		//create strategy chain
		dailyStrategy.setNextFareStrategy(weekStrategy);
		timeStrategy.setNextFareStrategy(dailyStrategy);

		FareProcessor processor = new FareProcessor(new CalculationServiceImpl(timeStrategy));
		File inputFile = new File(getClass().getClassLoader().getResource(TEST_GARBAGE_INPUT_FILE).getFile());
		CalculatorException calculatorException = assertThrows(CalculatorException.class, () -> {
			var data = Util.readFile(inputFile);
			processor.execute(data);
		}, "Error file");
		assertTrue(calculatorException.getMessage().contains(CalculatorException.INVALID_FILE));
	}

	@Test
	public void testDailyFare() throws CalculatorException
	{
		var weekStrategy = new WeeklyCappedFareStrategy();
		var dailyStrategy = new DailyCappedFareStrategy();
		var timeStrategy = new TimeBasedFareStrategy();
		//create strategy chain
		dailyStrategy.setNextFareStrategy(weekStrategy);
		timeStrategy.setNextFareStrategy(dailyStrategy);

		FareProcessor processor = new FareProcessor(new CalculationServiceImpl(timeStrategy));
		File inputFile = new File(getClass().getClassLoader().getResource(TEST_DAILY_INPUT_FILE).getFile());
		var data = Util.readFile(inputFile);
		assert (processor.execute(data) == 120.0);
	}

	@Test
	public void testWeeklyFare() throws CalculatorException
	{
		var weekStrategy = new WeeklyCappedFareStrategy();
		var dailyStrategy = new DailyCappedFareStrategy();
		var timeStrategy = new TimeBasedFareStrategy();
		//create strategy chain
		dailyStrategy.setNextFareStrategy(weekStrategy);
		timeStrategy.setNextFareStrategy(dailyStrategy);

		FareProcessor processor = new FareProcessor(new CalculationServiceImpl(timeStrategy));
		File inputFile = new File(getClass().getClassLoader().getResource(TEST_WEEKLY_INPUT_FILE).getFile());
		var data = Util.readFile(inputFile);
		assertTrue(processor.execute(data) == 700.0);
	}

	@Test
	public void testDiscountFare() throws CalculatorException
	{
		var weekStrategy = new WeeklyCappedFareStrategy();
		var dailyStrategy = new DailyCappedFareStrategy();
		var discountStrategy = new DiscountBasedFareStrategy();
		var timeStrategy = new TimeBasedFareStrategy();
		//create strategy chain
		dailyStrategy.setNextFareStrategy(weekStrategy);
		discountStrategy.setNextFareStrategy(dailyStrategy);
		timeStrategy.setNextFareStrategy(discountStrategy);

		FareProcessor processor = new FareProcessor(new CalculationServiceImpl(timeStrategy));
		File inputFile = new File(getClass().getClassLoader().getResource(TEST_DISCOUNT_INPUT_FILE).getFile());
		var data = Util.readFile(inputFile);
		assertTrue(processor.execute(data) == 120.0);
	}
}

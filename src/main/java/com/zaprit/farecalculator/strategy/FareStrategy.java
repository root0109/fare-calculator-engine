/**
 * 
 */
package com.zaprit.farecalculator.strategy;

import java.util.List;

import com.zaprit.farecalculator.model.Commute;
import com.zaprit.farecalculator.model.Tuple;

/**
 * @author vaibhav.singh
 */
public interface FareStrategy<T>
{
	public T calculate(List<Commute> commute, Tuple<Integer, Integer> farthestTravel);

	/*
	 * This is to enfornce that the contract is implemented to create a chain
	 * upto the user if they want to avoid it and do it via Constructor
	 */
	public void setNextFareStrategy(FareStrategy<T> fareStrategy);
}

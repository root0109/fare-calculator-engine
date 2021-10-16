/**
 * 
 */
package com.zaprit.farecalculator.strategy;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zaprit.farecalculator.FareConfig;
import com.zaprit.farecalculator.model.Commute;
import com.zaprit.farecalculator.model.Tuple;

/**
 * Age Above 60 > 30 %
 * @author vaibhav.singh
 */
public class DiscountBasedFareStrategy implements FareStrategy<Double>
{
	private static Logger        logger       = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private FareStrategy<Double> fareStrategy = null;

	@Override
	public Double calculate(List<Commute> commuteDTOList, Tuple<Integer, Integer> farthestTravel)
	{
		for (Commute commute : commuteDTOList)
		{
			setDiscountedFare(commute);
		}
		logger.log(Level.INFO, "DiscountBasedFareStrategy :" + commuteDTOList.toString());
		return fareStrategy.calculate(commuteDTOList, farthestTravel);
	}

	private void setDiscountedFare(Commute commute)
	{
		double discount = 0;
		for (Entry<Integer, Double> entry : FareConfig.getAgeDiscountMap().entrySet())
		{
			if (entry.getKey() <= commute.getAge())
			{
				discount = entry.getValue();
				break;
			}
		}
		commute.setFare(commute.getFare() + (discount * commute.getFare() / 100));
	}

	@Override
	public void setNextFareStrategy(FareStrategy<Double> fareStrategy)
	{
		this.fareStrategy = fareStrategy;
	}
}

/**
 * 
 */
package com.zaprit.farecalculator.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zaprit.farecalculator.FareConfig;
import com.zaprit.farecalculator.model.Commute;
import com.zaprit.farecalculator.model.Tuple;
import com.zaprit.farecalculator.util.Util;

/**
 * @author vaibhav.singh
 */
public class WeeklyCappedFareStrategy implements FareStrategy<Double>
{
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public Double calculate(List<Commute> commuteDTOList, Tuple<Integer, Integer> farthestTravel)
	{
		var remainingWeekFareMap = new HashMap<Integer, Double>();
		var totalWeekFareMap = new HashMap<Integer, Double>();

		var cappedFare = FareConfig.getWeeklyCapFareMap().get(farthestTravel.getX()).get(farthestTravel.getY());
		for (Commute commute : commuteDTOList)
		{
			int weekNo = Util.getWeekOfYear(commute.getLocalDate());
			var fare = remainingWeekFareMap.getOrDefault(weekNo, cappedFare);
			totalWeekFareMap.putIfAbsent(weekNo, cappedFare);
			var remainingFare = fare - commute.getFare();
			remainingWeekFareMap.put(weekNo, remainingFare);
			if (remainingFare < 0)
			{
				if (fare > 0)
					commute.setFare(fare);
				else
					commute.setFare(0);
				remainingWeekFareMap.put(weekNo, 0.0);
			}

		}
		calculateTotalPerWeek(remainingWeekFareMap, totalWeekFareMap);
		logger.log(Level.INFO, "WeeklyCappedFareStrategy :" + totalWeekFareMap.toString());
		return totalWeekFareMap.values().stream().reduce(0.0, Double::sum);
	}

	/**
	 * @param remainingWeekFareMap
	 * @param totalWeekFareMap
	 */
	private void calculateTotalPerWeek(Map<Integer, Double> remainingWeekFareMap, Map<Integer, Double> totalWeekFareMap)
	{
		for (Entry<Integer, Double> entry : remainingWeekFareMap.entrySet())
		{
			int weekNo = entry.getKey();
			totalWeekFareMap.put(weekNo, totalWeekFareMap.get(weekNo) - entry.getValue());
		}
	}

	@Override
	public void setNextFareStrategy(FareStrategy<Double> fareStrategy)
	{}
}

/**
 * 
 */
package com.zaprit.farecalculator.strategy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.zaprit.farecalculator.FareConfig;
import com.zaprit.farecalculator.model.Commute;
import com.zaprit.farecalculator.model.Tuple;

/**
 * @author vaibhav.singh
 */
public class DailyCappedFareStrategy implements FareStrategy<Double>
{
	private FareStrategy<Double> fareStrategy = null;

	@Override
	public Double calculate(List<Commute> commuteDTOList, Tuple<Integer, Integer> farthestTravel)
	{
		var remainingDayFareMap = new HashMap<LocalDate, Double>();
		var perDateFareMap = new HashMap<LocalDate, Double>();

		var cappedFare = FareConfig.getDailyCapFareMap().get(farthestTravel.getX()).get(farthestTravel.getY());
		for (Commute commute : commuteDTOList)
		{
			setFare(remainingDayFareMap, perDateFareMap, cappedFare, commute);
		}
		for (Entry<LocalDate, Double> entry : remainingDayFareMap.entrySet())
		{
			LocalDate date = entry.getKey();
			perDateFareMap.put(date, perDateFareMap.get(date) - entry.getValue());
		}
		return fareStrategy.calculate(commuteDTOList, farthestTravel);
	}

	private void setFare(HashMap<LocalDate, Double> remainingDayFareMap, HashMap<LocalDate, Double> perDateFareMap, Double cappedFare,
	                Commute commute)
	{
		var fare = remainingDayFareMap.getOrDefault(commute.getLocalDate(), cappedFare);
		perDateFareMap.putIfAbsent(commute.getLocalDate(), cappedFare);

		var remainingFare = fare - commute.getFare();
		remainingDayFareMap.put(commute.getLocalDate(), remainingFare);

		if (remainingFare <= 0)
		{
			if (fare > 0)
				commute.setFare(fare);
			else
				commute.setFare(0);
			remainingDayFareMap.put(commute.getLocalDate(), 0.0);
		}
	}

	@Override
	public void setNextFareStrategy(FareStrategy<Double> fareStrategy)
	{
		this.fareStrategy = fareStrategy;
	}
}

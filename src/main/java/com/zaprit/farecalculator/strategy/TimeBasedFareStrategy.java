/**
 * 
 */
package com.zaprit.farecalculator.strategy;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.zaprit.farecalculator.FareConfig;
import com.zaprit.farecalculator.model.Commute;
import com.zaprit.farecalculator.model.Tuple;

/**
 * @author vaibhav.singh
 */
public class TimeBasedFareStrategy implements FareStrategy<Double>
{
	private static Logger        logger       = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private FareStrategy<Double> fareStrategy = null;

	@Override
	public Double calculate(List<Commute> commuteDTOList, Tuple<Integer, Integer> farthestTravel)
	{
		int distance = -1;
		Tuple<Integer, Integer> tuple = null;
		for (Commute commute : commuteDTOList)
		{
			LocalTime time = getCommuteTime(commute.getTime());
			boolean peakTime = isPeakTiming(commute.getDayOfWeek(), time);
			var fare = peakTime ? FareConfig.getPeakZoneFareMap().get(commute.getFromZone()).get(commute.getToZone())
			                    : FareConfig.getOffPeakZoneFareMap().get(commute.getFromZone()).get(commute.getToZone());

			commute.setFare(fare);

			if (Math.abs(commute.getToZone() - commute.getFromZone()) > distance)
			{
				distance = Math.abs(commute.getToZone() - commute.getFromZone());
				tuple = new Tuple<Integer, Integer>(commute.getFromZone(), commute.getToZone());
			}
		}
		logger.log(Level.INFO, "TimeBasedFareStrategy :" + commuteDTOList.toString());
		return fareStrategy.calculate(commuteDTOList, tuple);
	}

	private boolean isPeakTiming(String dayOfWeek, LocalTime time)
	{
		boolean peakTime = false;
		var peakTiming = FareConfig.getPeakHourMap().get(DayOfWeek.valueOf(dayOfWeek.toUpperCase()));
		for (Entry<LocalTime, LocalTime> entry : peakTiming.entrySet())
		{
			if (entry.getKey().compareTo(time) < 0 && entry.getValue().compareTo(time) > 0)
			{
				peakTime = true;
				break;
			}
		}
		return peakTime;
	}

	private LocalTime getCommuteTime(String time)
	{
		int[] timeData = Stream.of(time.split(":")).mapToInt(Integer::parseInt).toArray();
		return timeData.length == 3 ? LocalTime.of(timeData[0], timeData[1], timeData[2]) : LocalTime.of(timeData[0], timeData[1], 0);
	}

	@Override
	public void setNextFareStrategy(FareStrategy<Double> fareStrategy)
	{
		this.fareStrategy = fareStrategy;
	}
}

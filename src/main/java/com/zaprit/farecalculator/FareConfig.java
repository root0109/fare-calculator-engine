/**
 * 
 */
package com.zaprit.farecalculator;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author vaibhav.singh
 */
public final class FareConfig
{
	// From zone to map < tozone , fare>>
	private static final Map<Integer, Map<Integer, Double>> dailyCapFareMap = new HashMap<>();

	// Date, From Time , To Time
	private static final Map<DayOfWeek, Map<LocalTime, LocalTime>> peakHourMap = new HashMap<>();
	// Date, From Time , To Time
	private static final Map<DayOfWeek, Map<LocalTime, LocalTime>> offPeakHourMap = new HashMap<>();
	// From zone to map < tozone , fare>>
	private static final Map<Integer, Map<Integer, Double>> peakZoneFareMap    = new HashMap<>();
	private static final Map<Integer, Map<Integer, Double>> offPeakZoneFareMap = new HashMap<>();

	// From zone to map < tozone , fare>>
	private static final Map<Integer, Map<Integer, Double>> weeklyCapFareMap = new HashMap<>();

	private static final Map<Integer, Double> ageDiscountMap = new TreeMap<>();
	static
	{
		ageDiscountMap.put(60, 30.0);
	}

	static
	{
		initialzeDailyCapFare();

		initializePeakHour();
		initializeOffPeakHour();
		initializePeakHourFare();
		initilzeOffPeakHourFare();

		initialzeWeeklyCapFare();
	}

	public static Map<Integer, Double> getAgeDiscountMap()
	{
		return ageDiscountMap;
	}

	public static Map<Integer, Map<Integer, Double>> getDailyCapFareMap()
	{
		return new HashMap<>(dailyCapFareMap);
	}

	public static Map<DayOfWeek, Map<LocalTime, LocalTime>> getPeakHourMap()
	{
		return new HashMap<>(peakHourMap);
	}

	public static Map<DayOfWeek, Map<LocalTime, LocalTime>> getOffPeakHourMap()
	{
		return new HashMap<>(offPeakHourMap);
	}

	public static Map<Integer, Map<Integer, Double>> getPeakZoneFareMap()
	{
		return new HashMap<>(peakZoneFareMap);
	}

	public static Map<Integer, Map<Integer, Double>> getOffPeakZoneFareMap()
	{
		return new HashMap<>(offPeakZoneFareMap);
	}

	public static Map<Integer, Map<Integer, Double>> getWeeklyCapFareMap()
	{
		return new HashMap<>(weeklyCapFareMap);
	}

	private static void initialzeDailyCapFare()
	{
		dailyCapFareMap.put(1, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 100.0);
				put(2, 120.0);
			}
		});
		dailyCapFareMap.put(2, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 120.0);
				put(2, 80.0);
			}
		});
	}

	private static void initilzeOffPeakHourFare()
	{
		offPeakZoneFareMap.put(1, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 25.0);
				put(2, 30.0);
			}
		});
		offPeakZoneFareMap.put(2, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 30.0);
				put(2, 20.0);
			}
		});
	}

	private static void initializePeakHourFare()
	{
		peakZoneFareMap.put(1, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 30.0);
				put(2, 35.0);
			}
		});
		peakZoneFareMap.put(2, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 35.0);
				put(2, 25.0);
			}
		});
	}

	private static void initializeOffPeakHour()
	{
		var weekDayOffPeakTiming = new HashMap<LocalTime, LocalTime>();
		weekDayOffPeakTiming.put(LocalTime.of(0, 0), LocalTime.of(6, 59, 59));
		weekDayOffPeakTiming.put(LocalTime.of(10, 30, 1), LocalTime.of(16, 59, 59));
		weekDayOffPeakTiming.put(LocalTime.of(20, 0, 1), LocalTime.of(23, 59, 59));

		var weekEndOffPeakTiming = new HashMap<LocalTime, LocalTime>();
		weekEndOffPeakTiming.put(LocalTime.of(0, 0), LocalTime.of(8, 59, 59));
		weekEndOffPeakTiming.put(LocalTime.of(11, 0, 1), LocalTime.of(17, 59, 59));
		weekEndOffPeakTiming.put(LocalTime.of(22, 0, 1), LocalTime.of(23, 59, 59));

		offPeakHourMap.put(DayOfWeek.MONDAY, weekDayOffPeakTiming);
		offPeakHourMap.put(DayOfWeek.TUESDAY, weekDayOffPeakTiming);
		offPeakHourMap.put(DayOfWeek.WEDNESDAY, weekDayOffPeakTiming);
		offPeakHourMap.put(DayOfWeek.THURSDAY, weekDayOffPeakTiming);
		offPeakHourMap.put(DayOfWeek.FRIDAY, weekDayOffPeakTiming);
		offPeakHourMap.put(DayOfWeek.SATURDAY, weekEndOffPeakTiming);
		offPeakHourMap.put(DayOfWeek.SUNDAY, weekEndOffPeakTiming);
	}

	private static void initializePeakHour()
	{
		var weekDayPeakTiming = new HashMap<LocalTime, LocalTime>();
		weekDayPeakTiming.put(LocalTime.of(7, 0, 0), LocalTime.of(10, 30, 0));
		weekDayPeakTiming.put(LocalTime.of(17, 0, 0), LocalTime.of(20, 0, 0));

		var weekEndPeakTiming = new HashMap<LocalTime, LocalTime>();
		weekEndPeakTiming.put(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0));
		weekEndPeakTiming.put(LocalTime.of(18, 0, 0), LocalTime.of(22, 0, 0));

		peakHourMap.put(DayOfWeek.MONDAY, weekDayPeakTiming);
		peakHourMap.put(DayOfWeek.TUESDAY, weekDayPeakTiming);
		peakHourMap.put(DayOfWeek.WEDNESDAY, weekDayPeakTiming);
		peakHourMap.put(DayOfWeek.THURSDAY, weekDayPeakTiming);
		peakHourMap.put(DayOfWeek.FRIDAY, weekDayPeakTiming);
		peakHourMap.put(DayOfWeek.SATURDAY, weekEndPeakTiming);
		peakHourMap.put(DayOfWeek.SUNDAY, weekEndPeakTiming);
	}

	private static void initialzeWeeklyCapFare()
	{
		weeklyCapFareMap.put(1, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 500.0);
				put(2, 600.0);
			}
		});
		weeklyCapFareMap.put(2, new HashMap<Integer, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, 600.0);
				put(2, 400.0);
			}
		});
	}
}

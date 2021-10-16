/**
 * 
 */
package com.zaprit.farecalculator.model;

import java.time.LocalDate;

import com.zaprit.farecalculator.constants.Konstants;
import com.zaprit.farecalculator.model.validator.Date;
import com.zaprit.farecalculator.model.validator.Time;
import com.zaprit.farecalculator.model.validator.Zone;
import com.zaprit.farecalculator.util.Util;

import lombok.Getter;

/**
 * Use Builder Pattern here to make sure code doesnot break later
 * @author vaibhav.singh
 */
@Getter
public class Commute
{
	private String date;
	private String time;
	private int    fromZone;
	private int    toZone;
	private int    age;
	//fare field is derived
	private double fare;

	private Commute(CommuteBuilder commuteBuilder)
	{
		super();
		this.date = commuteBuilder.date;
		this.time = commuteBuilder.time;
		this.fromZone = commuteBuilder.fromZone;
		this.toZone = commuteBuilder.toZone;
		this.age = commuteBuilder.age;
	}

	public String getDayOfWeek()
	{
		return LocalDate.parse(date, Konstants.formatter).getDayOfWeek().name();
	}

	public void setFare(double fare)
	{
		this.fare = fare;
	}

	public LocalDate getLocalDate()
	{
		return LocalDate.parse(date, Konstants.formatter);
	}

	public static class CommuteBuilder
	{
		@Date
		private String date;
		@Time
		private String time;
		@Zone
		private int    fromZone;
		@Zone
		private int    toZone;
		private int    age;

		public CommuteBuilder(String date, String time, int fromZone, int toZone)
		{
			this.date = date;
			this.time = time;
			this.fromZone = fromZone;
			this.toZone = toZone;
		}

		public Commute build()
		{
			var commute = new Commute(this);
			commute.setFare(age);
			return commute;
		}

		public CommuteBuilder setToZone(int toZone)
		{
			this.toZone = toZone;
			return this;
		}

		public CommuteBuilder setAge(int age)
		{
			this.age = age;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "Commute[date=" + date + ", time=" + time + ", weekNo=" + Util.getWeekOfYear(getLocalDate()) + ", fromZone=" + fromZone + ", toZone="
		       + toZone + ", fare=" + fare + "]\n";
	}
}

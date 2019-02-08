package com.filip.financesrest.models;


public class LocalDateProjection implements ILocalDateProjection
{

	private int month;
	private int year;

	public LocalDateProjection(int month, int year)
	{
		this.month = month;
		this.year = year;
	}

	@Override
	public int getMonth()
	{
		return month;
	}

	@Override
	public int getYear()
	{
		return year;
	}

}

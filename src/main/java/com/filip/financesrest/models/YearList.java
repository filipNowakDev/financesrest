package com.filip.financesrest.models;

import java.util.ArrayList;
import java.util.List;

public class YearList
{
	private List<Integer> years;

	public YearList()
	{
		years = new ArrayList<>();
	}

	public YearList(List<Integer> years)
	{
		this.years = years;
	}

	public void addYear(Integer year)
	{
		this.years.add(year);
	}

	public List<Integer> getYears()
	{
		return years;
	}
}

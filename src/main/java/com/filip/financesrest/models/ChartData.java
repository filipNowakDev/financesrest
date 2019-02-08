package com.filip.financesrest.models;


import java.util.ArrayList;
import java.util.List;

public class ChartData
{
	private List<String> labels;
	private List<Integer> series;

	public ChartData()
	{
		labels = new ArrayList<>();
		series = new ArrayList<>();
	}

	public List<String> getLabels()
	{
		return labels;
	}
	public List<Integer> getSeries()
	{
		return series;
	}
}

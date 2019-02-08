package com.filip.financesrest.controllers;


import com.filip.financesrest.models.Balance;
import com.filip.financesrest.models.ChartData;
import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.models.YearList;
import com.filip.financesrest.services.AnalysisService;
import com.filip.financesrest.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/analysis")
public class RestAnalysisController
{
	private CategoryService categoryService;
	private AnalysisService analysisService;

	@Autowired
	public RestAnalysisController(CategoryService categoryService, AnalysisService analysisService)
	{
		this.categoryService = categoryService;
		this.analysisService = analysisService;
	}

	@RequestMapping("/chart/date/{month}/{year}")
	public ChartData getDataInMonth(@PathVariable int month, @PathVariable int year, Authentication authentication)
	{

		ChartData chartData = new ChartData();

		List<EntryCategory> categories = categoryService.findByUser_Username(authentication.getName());
		for (EntryCategory category : categories)
		{
			chartData.getLabels().add(category.getName());
			chartData.getSeries().add(analysisService.getBalanceForMonthAndCategoryForUser(category.getId(), month, year,
					authentication.getName()));
		}

		return chartData;
	}

	@RequestMapping("/chart/year/{year}")
	public ChartData getDataInYear(@PathVariable int year, Authentication authentication)
	{
		ChartData chartData = new ChartData();
		for (int i = 0; i < 12; i++)
		{
			chartData.getLabels().add(Month.of(i + 1).getDisplayName(TextStyle.FULL, Locale.getDefault()));
			chartData.getSeries().add((int)analysisService.getBalanceForMonthAndYearForUser(i + 1, year,
					authentication.getName()).getBalance());
		}

		return chartData;
	}

	@RequestMapping("/years")
	public YearList getDistinctUserYears(Authentication authentication)
	{
		return analysisService.getDistinctYearsForUser(authentication.getName());
	}

	@RequestMapping("/dates")
	public List<LocalDate> getDistinctUserYearsAndMonths(Authentication authentication)
	{
		return analysisService.getDistinctMonthsAndYearsForUser(authentication.getName());
	}

	@RequestMapping("/balance/{month}/{year}")
	public Balance getBalance(@PathVariable int month, @PathVariable int year, Authentication authentication)
	{
		return analysisService.getBalanceForMonthAndYearForUser(month, year, authentication.getName());
	}

}

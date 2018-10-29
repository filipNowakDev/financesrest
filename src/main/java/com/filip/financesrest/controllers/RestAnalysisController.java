package com.filip.financesrest.controllers;


import com.filip.financesrest.models.ChartData;
import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.services.CategoryService;
import com.filip.financesrest.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/analysis")
public class RestAnalysisController
{
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private EntryService entryService;

	@RequestMapping("/chart/date/{month}/{year}")
	public ChartData getDataInMonth(@PathVariable int month, @PathVariable int year)
	{

		ChartData chartData = new ChartData();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated())
		{
			List<EntryCategory> categories = categoryService.findByUser_Username(authentication.getName());
			for (EntryCategory category : categories)
			{
				chartData.getLabels().add(category.getName());
				chartData.getSeries().add(entryService.getBalanceForMonthAndCategory(category.getId(), month, year));
			}
		}
		return chartData;

	}

}

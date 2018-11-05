package com.filip.financesrest.controllers;


import com.filip.financesrest.services.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/analysis")
public class AnalysisController
{

	private AnalysisService analysisService;

	@Autowired
	public AnalysisController(AnalysisService analysisService)
	{
		this.analysisService = analysisService;
	}


	@RequestMapping(method = RequestMethod.GET)
	public String viewCharts(Model model, Authentication authentication)
	{
		List<LocalDate> dates = analysisService.getDistinctMonthsAndYearsForUser(authentication.getName());
		for (LocalDate date : dates)
		{
			System.out.print("[DEBUG] Dates info| " + date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " = " + date.getMonth().getValue() + " | " + date.getYear() + " |\n");
		}
		model.addAttribute("dates", dates);
		model.addAttribute("style", TextStyle.FULL);
		model.addAttribute("locale", Locale.getDefault());
		return "chart";
	}
}

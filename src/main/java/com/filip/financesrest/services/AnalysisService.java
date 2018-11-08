package com.filip.financesrest.services;

import com.filip.financesrest.models.Balance;
import com.filip.financesrest.models.YearList;

import java.time.LocalDate;
import java.util.List;

public interface AnalysisService
{
	List<LocalDate> getDistinctMonthsAndYearsForUser(String username);

	int getBalanceForMonthAndCategoryForUser(long categoryId, int month, int year, String username);

	YearList getDistinctYearsForUser(String username);

	Balance getBalanceForMonthAndYearForUser(int month, int year, String username);


}

package com.filip.financesrest.services;

import java.time.LocalDate;
import java.util.List;

public interface AnalysisService
{
	List<LocalDate> getDistinctMonthsAndYearsForUser(String username);

	int getBalanceForMonthAndCategoryForUser(long categoryId, int month, int year, String username);

}

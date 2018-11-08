package com.filip.financesrest.service_tests;


import com.filip.financesrest.models.ILocalDateProjection;
import com.filip.financesrest.models.LocalDateProjection;
import com.filip.financesrest.repositories.EntryRepository;
import com.filip.financesrest.services.AnalysisService;
import com.filip.financesrest.services.AnalysisServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)

public class AnalysisServiceImplTest
{

	@Mock
	private EntryRepository entryRepository;

	private AnalysisService analysisService;


	@Before
	public void setUp()
	{
		analysisService = new AnalysisServiceImpl(entryRepository);
	}


	@Test
	public void getDistinctMonthsAndYearsForUser_returnsListOfLocalDate()
	{

		ILocalDateProjection date = new LocalDateProjection(10, 2018);
		ILocalDateProjection date2 = new LocalDateProjection(11, 2018);

		List<ILocalDateProjection> dateProjections = new ArrayList<>();
		dateProjections.add(date);
		dateProjections.add(date2);


		given(entryRepository.selectDistinctEntryMonths("test")).willReturn(dateProjections);

		List<LocalDate> localDates = analysisService.getDistinctMonthsAndYearsForUser("test");


		assertThat(localDates.size()).isEqualTo(2);
		assertThat(localDates.get(0).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())).isEqualTo("October");
		assertThat(localDates.get(1).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())).isEqualTo("November");
	}


	@Test
	public void getBalanceForMonthAndCategoryForUser_returnBalance()
	{
		given(entryRepository.selectBalanceByCategoryAndMonth(1, 10, 2018, "user")).willReturn(123);
		given(entryRepository.selectBalanceByCategoryAndMonth(1, 4, 2018, "user")).willReturn(null);

		int balance_pos = analysisService.getBalanceForMonthAndCategoryForUser(1, 10, 2018, "user");
		int balance_neg = analysisService.getBalanceForMonthAndCategoryForUser(1, 4, 2018, "user");

		assertThat(balance_pos).isEqualTo(123);
		assertThat(balance_neg).isEqualTo(0);
	}
}

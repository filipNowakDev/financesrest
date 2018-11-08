package com.filip.financesrest.services;

import com.filip.financesrest.models.Balance;
import com.filip.financesrest.models.ILocalDateProjection;
import com.filip.financesrest.models.YearList;
import com.filip.financesrest.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class AnalysisServiceImpl implements AnalysisService
{
	private EntryRepository entryRepository;


	@Autowired
	public AnalysisServiceImpl(EntryRepository entryRepository)
	{
		this.entryRepository = entryRepository;
	}

	@Override
	public List<LocalDate> getDistinctMonthsAndYearsForUser(String username)
	{
		List<ILocalDateProjection> datesList = entryRepository.selectDistinctEntryMonths(username);
		List<LocalDate> list = new ArrayList<>();
		for (ILocalDateProjection dateProj : datesList)
			list.add(LocalDate.of(dateProj.getYear(), dateProj.getMonth(), 1));
		return list;
	}

	@Override
	public int getBalanceForMonthAndCategoryForUser(long categoryId, int month, int year, String username)
	{
		Integer balance = entryRepository.selectBalanceByCategoryAndMonth(categoryId, month, year, username);
		if (balance != null)
			return balance;
		else
			return 0;
	}

	@Override
	public YearList getDistinctYearsForUser(String username)
	{
		return new YearList(entryRepository.selectDistinctYearsForUser(username));
	}

	@Override
	public Balance getBalanceForMonthAndYearForUser(int month, int year, String username)
	{
		Double balance = entryRepository.selectBalanceForMonthAndYearForUser(month, year, username);
		if(balance == null)
			balance = 0.0;
		return new Balance(balance);
	}
}

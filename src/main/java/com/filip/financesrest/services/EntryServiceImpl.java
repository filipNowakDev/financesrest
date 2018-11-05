package com.filip.financesrest.services;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.ILocalDateProjection;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.CategoryRepository;
import com.filip.financesrest.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntryServiceImpl implements EntryService
{
	private  EntryRepository entryRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;

	@Autowired
	public EntryServiceImpl(EntryRepository entryRepository, UserService userService, CategoryRepository categoryRepository)
	{
		this.entryRepository = entryRepository;
		this.userService = userService;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void save(FinanceEntry entry)
	{
		entryRepository.save(entry);
	}

	@Override
	public void delete(Long id)
	{
		entryRepository.delete(id);
	}


	@Override
	public FinanceEntry findOne(Long id)
	{
		return entryRepository.findOne(id);
	}

	@Override
	public List<FinanceEntry> findAll()
	{
		return entryRepository.findAll();
	}

	@Override
	public String getOwnerName(Long id)
	{
		FinanceEntry entry = entryRepository.findOne(id);
		return entry.getUser().getUsername();
	}

	@Override
	public Boolean isOwner(User user, Long id)
	{
		FinanceEntry entry = entryRepository.findOne(id);
		return entry.getUser().getUsername().equals(user.getUsername());

	}

	@Override
	public Boolean isOwner(Authentication authentication, Long id)
	{
		FinanceEntry entry = entryRepository.findOne(id);
		return authentication.getName().equals(entry.getUser().getUsername());
	}

	@Override
	public List<FinanceEntry> findByUser_UsernameOrderByDateAsc(String username)
	{
		return entryRepository.findByUser_UsernameOrderByDateAsc(username);
	}

	@Override
	public List<FinanceEntry> findByUser_UsernameOrderByDateDesc(String username)
	{
		return entryRepository.findByUser_UsernameOrderByDateDesc(username);
	}

	@Override
	public List<FinanceEntry> findByUser_UsernameOrderByValueAsc(String username)
	{
		return entryRepository.findByUser_UsernameOrderByValueAsc(username);
	}

	@Override
	public List<FinanceEntry> findByUser_UsernameOrderByValueDesc(String username)
	{
		return entryRepository.findByUser_UsernameOrderByValueDesc(username);
	}

	@Override
	public List<FinanceEntry> findByUser_UsernameAndCategory_Id(String username, Long id)
	{
		return entryRepository.findByUser_UsernameAndCategory_Id(username, id);
	}

	@Override
	public Double getBalance(Authentication authentication)
	{
		User user = userService.findByUsername(authentication.getName());
		List<FinanceEntry> entries = user.getEntries();
		Double sum = new Double(0);
		for (FinanceEntry entry : entries)
		{
			sum += entry.getValue();
		}
		return sum;
	}

	@Override
	public Double getBalanceByCategory(Long categoryId)
	{
		List<FinanceEntry> entries = categoryRepository.findOne(categoryId).getEntries();
		Double sum = new Double(0);
		for (FinanceEntry entry : entries)
		{
			sum += entry.getValue();
		}
		return sum;
	}

	@Override
	public List<FinanceEntry> getSortedBy(String field, String order, Authentication authentication)
	{
		List<FinanceEntry> entries;

		if (field.equals("date"))
		{
			if (order.equals("desc"))
				entries = entryRepository.findByUser_UsernameOrderByDateDesc(authentication.getName());
			else
				entries = entryRepository.findByUser_UsernameOrderByDateAsc(authentication.getName());
		} else if (field.equals("value"))
		{
			if (order.equals("desc"))
				entries = entryRepository.findByUser_UsernameOrderByValueDesc(authentication.getName());
			else
				entries = entryRepository.findByUser_UsernameOrderByValueAsc(authentication.getName());
		} else
			entries = entryRepository.findByUser_UsernameOrderByDateAsc(authentication.getName());
		return entries;
	}

	@Override
	public List<FinanceEntry> getSortedBy(Long categoryId, String field, String order, Authentication authentication)
	{
		List<FinanceEntry> entries;
		if (field.equals("date"))
		{
			if (order.equals("desc"))
				entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateDesc(authentication.getName(), categoryId);
			else
				entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateAsc(authentication.getName(), categoryId);
		} else if (field.equals("value"))
		{
			if (order.equals("desc"))
				entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateAsc(authentication.getName(), categoryId);
			else
				entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByValueDesc(authentication.getName(), categoryId);
		} else
			entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateDesc(authentication.getName(), categoryId);
		return entries;
	}

	@Override
	public List<LocalDate> getDistinctMonthsAndYears(String username)
	{
		List<ILocalDateProjection> datesList = entryRepository.selectDistinctEntryMonths(username);
		List<LocalDate> list = new ArrayList<>();
		for (ILocalDateProjection dateProj: datesList)
		{
			list.add(LocalDate.of(dateProj.getYear(), dateProj.getMonth().getValue() - 1, 1));
		}
		return list;

	}

	@Override
	public Integer getBalanceForMonthAndCategory(long categoryId, int month, int year, String username)
	{

		Integer balance = entryRepository.selectBalanceByMonth(categoryId, month, year, username);
		if (balance != null)
			return balance;
		else
			return 0;
	}
}

package com.filip.financesrest.services;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.CategoryRepository;
import com.filip.financesrest.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService
{
	private EntryRepository entryRepository;
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
		return entry != null && entry.getUser().getUsername().equals(user.getUsername());

	}

	@Override
	public Boolean isOwner(Authentication authentication, Long id)
	{
		FinanceEntry entry = entryRepository.findOne(id);
		return entry != null && authentication.getName().equals(entry.getUser().getUsername());
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
		double sum = (double) 0;
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
		double sum = (double) 0;
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

		switch (field)
		{
			case "date":
				if (order.equals("desc"))
					entries = entryRepository.findByUser_UsernameOrderByDateDesc(authentication.getName());
				else
					entries = entryRepository.findByUser_UsernameOrderByDateAsc(authentication.getName());
				break;
			case "value":
				if (order.equals("desc"))
					entries = entryRepository.findByUser_UsernameOrderByValueDesc(authentication.getName());
				else
					entries = entryRepository.findByUser_UsernameOrderByValueAsc(authentication.getName());
				break;
			default:
				entries = entryRepository.findByUser_UsernameOrderByDateAsc(authentication.getName());
				break;
		}
		return entries;
	}

	@Override
	public List<FinanceEntry> getSortedBy(Long categoryId, String field, String order, Authentication authentication)
	{
		List<FinanceEntry> entries;
		switch (field)
		{
			case "date":
				if (order.equals("desc"))
					entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateDesc(authentication.getName(), categoryId);
				else
					entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateAsc(authentication.getName(), categoryId);
				break;
			case "value":
				if (order.equals("desc"))
					entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateAsc(authentication.getName(), categoryId);
				else
					entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByValueDesc(authentication.getName(), categoryId);
				break;
			default:
				entries = entryRepository.findByUser_UsernameAndCategory_IdOrderByDateDesc(authentication.getName(), categoryId);
				break;
		}
		return entries;
	}
}

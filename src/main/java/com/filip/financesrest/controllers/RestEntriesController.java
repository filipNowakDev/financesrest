package com.filip.financesrest.controllers;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.services.CategoryService;
import com.filip.financesrest.services.EntryService;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/entries")
public class RestEntriesController
{

	private EntryService entryService;
	private UserService userService;
	private CategoryService categoryService;

	@Autowired
	public RestEntriesController(EntryService entryService, UserService userService, CategoryService categoryService)
	{
		this.entryService = entryService;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<FinanceEntry> getAll(Authentication authentication)
	{
		User currentUser = userService.findByUsername(authentication.getName());
		return currentUser.getEntries();
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public List<FinanceEntry> getByCategory(@PathVariable long id, Authentication authentication)
	{
		if (categoryService.isOwner(authentication, id))
			return categoryService.findOne(id).getEntries();
		else
			return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	public List<FinanceEntry> save(@RequestBody FinanceEntry input)
	{
		entryService.save(input);
		return this.entryService.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public List<FinanceEntry> delete(@PathVariable long id, Authentication authentication)
	{
		if (entryService.isOwner(authentication, id))
			entryService.delete(id);
		return this.entryService.findAll();
	}
}

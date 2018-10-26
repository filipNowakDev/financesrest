package com.filip.financesrest.controllers;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntryRepository;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/entries")
public class RestEntriesController
{

    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<FinanceEntry> getAll(Authentication authentication)
    {
        User currentUser = userService.findByUsername(authentication.getName());
        return currentUser.getEntries();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<FinanceEntry> save(@RequestBody FinanceEntry input)
    {
        entryRepository.save(input);
        return this.entryRepository.findAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public List<FinanceEntry> save(@PathVariable long id)
    {
        entryRepository.delete(id);
        return this.entryRepository.findAll();
    }
}

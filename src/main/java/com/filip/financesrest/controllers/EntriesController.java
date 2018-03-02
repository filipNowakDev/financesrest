package com.filip.financesrest.controllers;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntriesRepository;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/entries")
public class EntriesController
{

    @Autowired
    private EntriesRepository entriesRepository;
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Set<FinanceEntry> getAll(Authentication authentication)
    {
        User currentUser = userService.findByUsername(authentication.getName());
        return currentUser.getEntries();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<FinanceEntry> save(@RequestBody FinanceEntry input)
    {
        entriesRepository.save(input);
        return this.entriesRepository.findAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public List<FinanceEntry> save(@PathVariable long id)
    {
        entriesRepository.delete(id);
        return this.entriesRepository.findAll();
    }
}

package com.filip.financesrest.controllers;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntryRepository;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/balance")
public class RestBalanceController
{
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public Set<FinanceEntry> getBalance(Authentication authentication)
    {
        User currentUser = userService.findByUsername(authentication.getName());

        List<FinanceEntry> entries = currentUser.getEntries();
        double sum = 0;

        for (FinanceEntry entry : entries)
        {
            sum += entry.getValue();
        }
        FinanceEntry result = new FinanceEntry("Balance", sum, null, currentUser, null);
        Set<FinanceEntry> reslist = new HashSet<>();
        reslist.add(result);
        return reslist;
    }
}

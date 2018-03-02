package com.filip.financesrest.controllers;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.repositories.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/balance")
public class BalanceController
{
    @Autowired
    private EntriesRepository entriesRepository;


    @RequestMapping(method = RequestMethod.GET)
    public List<FinanceEntry> getBalance()
    {
        List<FinanceEntry> entries = this.entriesRepository.findAll();
        double sum = 0;

        for (FinanceEntry entry : entries)
        {
            sum += entry.getValue();
        }
        FinanceEntry result = new FinanceEntry("Balance", sum);
        List<FinanceEntry> reslist = new ArrayList<>();
        reslist.add(result);
        return reslist;
    }
}

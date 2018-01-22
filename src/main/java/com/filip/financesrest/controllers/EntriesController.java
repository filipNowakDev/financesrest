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
@RequestMapping("/entries")
public class EntriesController {

    @Autowired
    private EntriesRepository entriesRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<FinanceEntry> getAll(){
        return this.entriesRepository.findAll();
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public List<FinanceEntry> getBalance(){
        List<FinanceEntry> entries = this.entriesRepository.findAll();
        double sum = 0;

        for(FinanceEntry entry : entries){
            sum += entry.getValue();
        }
        FinanceEntry result = new FinanceEntry("Balance", sum);
        List <FinanceEntry> reslist = new ArrayList<FinanceEntry>();
        reslist.add(result);
        return reslist;
    }

}

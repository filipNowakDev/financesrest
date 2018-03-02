package com.filip.financesrest.controllers;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.repositories.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/entries")
public class EntriesController
{

    @Autowired
    private EntriesRepository entriesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<FinanceEntry> getAll()
    {
        return this.entriesRepository.findAll();
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

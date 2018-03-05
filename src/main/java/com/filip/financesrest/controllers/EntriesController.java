package com.filip.financesrest.controllers;


import com.filip.financesrest.components.EntryValidator;
import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntriesRepository;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = "/entries")
public class EntriesController
{

    @Autowired
    private EntryValidator entryValidator;
    @Autowired
    private EntriesRepository entriesRepository;
    @Autowired
    private UserService userService;



    @RequestMapping(method = RequestMethod.GET)
    public String allEntries(Model model, Authentication authentication)
    {
        User currentUser = userService.findByUsername(authentication.getName());
        List<FinanceEntry> entries = currentUser.getEntries();
        Collections.sort(entries, Comparator.comparing(FinanceEntry::getDate));


        model.addAttribute("entries", entries);
        model.addAttribute("balance", getBalance(authentication));
        return "index";
    }

    @RequestMapping(value = "/{sort}", method = RequestMethod.GET)
    public String allEntries(@PathVariable String sort, Model model, Authentication authentication)
    {
        User currentUser = userService.findByUsername(authentication.getName());
        List<FinanceEntry> entries = currentUser.getEntries();

        if(sort.equals("date"))
            Collections.sort(entries, Comparator.comparing(FinanceEntry::getDate));
        else if(sort.equals("value"))
            Collections.sort(entries, Comparator.comparing(FinanceEntry::getValue));
        else
            Collections.sort(entries, Comparator.comparing(FinanceEntry::getDate));


        model.addAttribute("entries", entries);
        model.addAttribute("balance", getBalance(authentication));
        return "index";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String entryForm(Model model)
    {
        model.addAttribute("entryForm", new FinanceEntry());
        return "entryform";
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addEntry(@ModelAttribute("entryForm") FinanceEntry entryForm, BindingResult bindingResult, Model model, Authentication authentication)
    {
        entryValidator.validate(entryForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            return "entryform";
        }

        entryForm.setUser(userService.findByUsername(authentication.getName()));
        entriesRepository.save(entryForm);
        return "redirect:/entries";
    }

    private Double getBalance(Authentication authentication)
    {
        User user = userService.findByUsername(authentication.getName());
        List<FinanceEntry> entries = user.getEntries();
        Double sum = new Double(0);
        for(FinanceEntry entry : entries)
        {
            sum += entry.getValue();
        }
        return sum;
    }
}

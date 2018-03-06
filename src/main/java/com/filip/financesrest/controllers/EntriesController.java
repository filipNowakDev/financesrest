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
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{sort}/{order}", method = RequestMethod.GET)
    public String allEntries(@PathVariable String sort, @PathVariable String order, Model model, Authentication authentication)
    {
        User currentUser = userService.findByUsername(authentication.getName());
        List<FinanceEntry> entries = currentUser.getEntries();

        if(sort.equals("date"))
        {
            if(order.equals("desc"))
                Collections.sort(entries, Comparator.comparing(FinanceEntry::getDate).reversed());
            else
                Collections.sort(entries, Comparator.comparing(FinanceEntry::getDate));
        }

        else if(sort.equals("value"))
        {
            if(order.equals("desc"))
                Collections.sort(entries, Comparator.comparing(FinanceEntry::getValue).reversed());
            else
                Collections.sort(entries, Comparator.comparing(FinanceEntry::getValue));

        }
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

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEntryForm(@PathVariable Long id, Model model, Authentication authentication)
    {
        FinanceEntry entry = entriesRepository.findOne(id);
        if(entry.getUser().getUsername().equals(authentication.getName()))
        {
            model.addAttribute("entry", entry);
            return "entryedit";
        }
        else
            return "redirect:/accessDenied";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editEntry(@PathVariable Long id, @ModelAttribute("entry") FinanceEntry entry, Model model, BindingResult bindingResult, Authentication authentication)
    {
        entryValidator.validate(entry, bindingResult);

        if (bindingResult.hasErrors())
        {
            return "entryedit";
        }

        FinanceEntry testEntry = entriesRepository.findOne(id);
        if(testEntry.getUser().getUsername().equals(authentication.getName()))
        {
            entry.setUser(userService.findByUsername(authentication.getName()));
            entry.setId(id);
            entriesRepository.save(entry);
            return "redirect:/";
        }
        else
            return "redirect:/accessDenied";

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteEntry(@PathVariable Long id, Authentication authentication)
    {
        FinanceEntry entry = entriesRepository.findOne(id);
        if(entry.getUser().getUsername().equals(authentication.getName()))
        {
            entriesRepository.delete(id);
            return "redirect:/entries";
        }
        else
            return "redirect:/accessDenied";
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

package com.filip.financesrest.controllers;


import com.filip.financesrest.components.EntryValidator;
import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.services.EntryService;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private EntryService entryService;
    @Autowired
    private UserService userService;



    @RequestMapping(method = RequestMethod.GET)
    public String allEntries(Model model, Authentication authentication)
    {
        List<FinanceEntry> entries = entryService.findByUser_UsernameOrderByDateDesc(authentication.getName());


        model.addAttribute("entries", entries);
        model.addAttribute("balance", entryService.getBalance(authentication));
        return "index";
    }

    @RequestMapping(value = "/{sort}/{order}", method = RequestMethod.GET)
    public String allEntries(@PathVariable String sort, @PathVariable String order, Model model, Authentication authentication)
    {
        List<FinanceEntry> entries;

        if(sort.equals("date"))
        {
            if(order.equals("desc"))
                entries = entryService.findByUser_UsernameOrderByDateDesc(authentication.getName());
            else
                entries = entryService.findByUser_UsernameOrderByDateAsc(authentication.getName());
        }

        else if(sort.equals("value"))
        {
            if(order.equals("desc"))
                entries = entryService.findByUser_UsernameOrderByValueDesc(authentication.getName());
            else
                entries = entryService.findByUser_UsernameOrderByValueAsc(authentication.getName());
        }
        else
            entries = entryService.findByUser_UsernameOrderByDateAsc(authentication.getName());


        model.addAttribute("entries", entries);
        model.addAttribute("balance", entryService.getBalance(authentication));
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
        entryService.save(entryForm);
        return "redirect:/entries";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEntryForm(@PathVariable Long id, Model model, Authentication authentication)
    {

        if(entryService.isOwner(authentication, id))
        {
            FinanceEntry entry = entryService.findOne(id);
            model.addAttribute("entry", entry);
            return "entryedit";
        }
        else
            return "redirect:/accessDenied";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editEntry(@PathVariable Long id, @ModelAttribute("entry") FinanceEntry entry, BindingResult bindingResult, Model model, Authentication authentication)
    {
        entryValidator.validate(entry, bindingResult);

        if (bindingResult.hasErrors())
        {
            return "entryedit";
        }


        if(entryService.isOwner(authentication, id))
        {
            entry.setUser(userService.findByUsername(authentication.getName()));
            entry.setId(id);
            entryService.save(entry);
            return "redirect:/";
        }
        else
            return "redirect:/accessDenied";

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteEntry(@PathVariable Long id, Authentication authentication)
    {
        FinanceEntry entry = entryService.findOne(id);
        if(entry.getUser().getUsername().equals(authentication.getName()))
        {
            entryService.delete(id);
            return "redirect:/entries";
        }
        else
            return "redirect:/accessDenied";
    }
}

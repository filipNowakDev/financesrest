package com.filip.financesrest.controllers;


import com.filip.financesrest.components.EntryValidator;
import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.repositories.CategoryRepository;
import com.filip.financesrest.services.EntryService;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private CategoryRepository categoryRepository;



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
    public String entryForm(Model model, Authentication authentication)
    {
        model.addAttribute("entryForm", new FinanceEntry());
        model.addAttribute("categories", categoryRepository.findByUser_Username(authentication.getName()));
        return "entryform";
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addEntry(@ModelAttribute("entryForm") FinanceEntry entryForm, BindingResult bindingResult, @RequestParam long categoryId, Model model, Authentication authentication)
    {
        entryValidator.validate(entryForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            model.addAttribute("entryForm", new FinanceEntry());
            model.addAttribute("categories", categoryRepository.findByUser_Username(authentication.getName()));
            return "entryform";
        }

        entryForm.setUser(userService.findByUsername(authentication.getName()));
        entryForm.setCategory(categoryRepository.findOne(categoryId));
        entryService.save(entryForm);
        return "redirect:/entries";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEntryForm(@PathVariable Long id, Model model, Authentication authentication)
    {

        if(entryService.isOwner(authentication, id))
        {
            FinanceEntry entryForm = entryService.findOne(id);
            model.addAttribute("entryForm", entryForm);
            model.addAttribute("categories", categoryRepository.findByUser_Username(authentication.getName()));
            return "entryedit";
        }
        else
            return "redirect:/accessDenied";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editEntry(@PathVariable Long id, @ModelAttribute("entry") FinanceEntry entryForm, BindingResult bindingResult, @RequestParam long categoryId, Model model, Authentication authentication)
    {
        entryValidator.validate(entryForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            model.addAttribute("entryForm", entryForm);
            model.addAttribute("categories", categoryRepository.findByUser_Username(authentication.getName()));
            return "entryedit";
        }


        if(entryService.isOwner(authentication, id))
        {
            entryForm.setUser(userService.findByUsername(authentication.getName()));
            entryForm.setId(id);
            entryForm.setCategory(categoryRepository.findOne(categoryId));
            entryService.save(entryForm);
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

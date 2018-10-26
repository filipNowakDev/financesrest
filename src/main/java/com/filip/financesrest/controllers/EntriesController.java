package com.filip.financesrest.controllers;


import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.services.CategoryService;
import com.filip.financesrest.services.EntryService;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/entries")
public class EntriesController
{

    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;



    @RequestMapping(method = RequestMethod.GET)
    public String allEntries(Model model, Authentication authentication)
    {
        List<FinanceEntry> entries = entryService.findByUser_UsernameOrderByDateDesc(authentication.getName());

        model.addAttribute("baseUrl", "/entries/");
        model.addAttribute("entries", entries);
        model.addAttribute("balance", entryService.getBalance(authentication));
        return "index";
    }



    @RequestMapping(value = "/{sort}/{order}", method = RequestMethod.GET)
    public String allEntries(@PathVariable String sort, @PathVariable String order, Model model, Authentication authentication)
    {
        List<FinanceEntry> entries = entryService.getSortedBy(sort, order, authentication);

        model.addAttribute("baseUrl", "/entries/");
        model.addAttribute("entries", entries);
        model.addAttribute("balance", entryService.getBalance(authentication));
        return "index";
    }

    @RequestMapping(value = "/category/{id}")
    public String entriesByCategory(@PathVariable Long id, Model model, Authentication authentication)
    {
        if(categoryService.exists(id) && categoryService.isOwner(authentication, id))
        {
            model.addAttribute("baseUrl", "/entries/category/" + id + "/");
            model.addAttribute("entries", categoryService.findOne(id).getEntries());
            model.addAttribute("balance", entryService.getBalanceByCategory(id));
            return "index";
        }
        else
            return "redirect:/accessDenied";
    }

    @RequestMapping(value = "/category/{id}/{sort}/{order}", method = RequestMethod.GET)
    public String sortedEntriesByCategory(@PathVariable Long id, @PathVariable String sort,
                                          @PathVariable String order, Model model, Authentication authentication)
    {
        if(categoryService.exists(id) && categoryService.isOwner(authentication, id))
        {
            List<FinanceEntry> entries = entryService.getSortedBy(id, sort, order, authentication);
            model.addAttribute("baseUrl", "/entries/category/" + id + "/");
            model.addAttribute("entries", entries);
            model.addAttribute("balance", entryService.getBalance(authentication));
            return "index";
        }
        else
            return "redirect:/accessDenied";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String entryForm(Model model, Authentication authentication)
    {
        model.addAttribute("entryForm", new FinanceEntry());
        model.addAttribute("categories", categoryService.findByUser_UsernameOrderByName(authentication.getName()));
        return "entryform";
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addEntry(@Valid @ModelAttribute("entryForm") FinanceEntry entryForm, BindingResult bindingResult, Model model, Authentication authentication)
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("categories", categoryService.findByUser_Username(authentication.getName()));
            return "entryform";
        }

        entryForm.setUser(userService.findByUsername(authentication.getName()));
        entryService.save(entryForm);
        return "redirect:/entries";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEntryForm(@PathVariable Long id, Model model, Authentication authentication)
    {

        FinanceEntry entryForm = entryService.findOne(id);
        if(entryForm == null)
        {
            return "redirect:/accessDenied";
        }
        if(entryService.isOwner(authentication, id))
        {
            model.addAttribute("entryForm", entryForm);
            model.addAttribute("categories", categoryService.findByUser_UsernameOrderByName(authentication.getName()));
            return "entryform";
        }
        else
            return "redirect:/accessDenied";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteEntry(@PathVariable Long id, Authentication authentication)
    {
        FinanceEntry entry = entryService.findOne(id);
        if(entry == null)
        {
            return "redirect:/accessDenied";
        }
        if(entry.getUser().getUsername().equals(authentication.getName()))
        {
            entryService.delete(id);
            return "redirect:/entries";
        }
        else
            return "redirect:/accessDenied";
    }
}

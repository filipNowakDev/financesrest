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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "redirect:/";

    }
}

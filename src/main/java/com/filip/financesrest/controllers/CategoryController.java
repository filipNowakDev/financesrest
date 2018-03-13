package com.filip.financesrest.controllers;


import com.filip.financesrest.components.CategoryValidator;
import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.repositories.CategoryRepository;
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
@RequestMapping("/categories")
public class CategoryController
{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryValidator categoryValidator;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String categoryForm(Model model)
    {
        model.addAttribute("categoryForm", new EntryCategory());
        return "categoryform";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute("categoryForm") EntryCategory categoryForm, BindingResult bindingResult, Model model, Authentication authentication)
    {
        categoryValidator.validate(categoryForm, bindingResult);

        if (bindingResult.hasErrors())
        {
            return "categoryform";
        }

        categoryForm.setUser(userService.findByUsername(authentication.getName()));
        categoryRepository.save(categoryForm);
        return "redirect:/entries";
    }
}

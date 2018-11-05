package com.filip.financesrest.controllers;


import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.services.CategoryService;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController
{
    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService)
    {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String allCategories(Model model, Authentication authentication)
    {
        return "categories";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String categoryForm(Model model)
    {
        model.addAttribute("categoryForm", new EntryCategory());
        return "categoryform";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addCategory(@Valid @ModelAttribute("categoryForm") EntryCategory categoryForm, BindingResult bindingResult, Model model, Authentication authentication)
    {
        if (bindingResult.hasErrors())
        {
            return "categoryform";
        }

        categoryForm.setUser(userService.findByUsername(authentication.getName()));
        categoryService.save(categoryForm);
        return "redirect:/entries";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String categoryEditForm(@PathVariable Long id, Model model, Authentication authentication)
    {

        if(categoryService.exists(id) && categoryService.isOwner(authentication, id))
        {
            EntryCategory category = categoryService.findOne(id);
            model.addAttribute("categoryForm", category);
            return "categoryform";
        }
        else
            return "redirect:/accessDenied";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteEntry(@PathVariable Long id, Authentication authentication)
    {
        EntryCategory category = categoryService.findOne(id);
        if(category == null && !categoryService.isOwner(authentication, id))
        {
            return "redirect:/accessDenied";
        }
        if(category.getUser().getUsername().equals(authentication.getName()))
        {
            categoryService.delete(id);
            return "redirect:/entries";
        }
        else
            return "redirect:/accessDenied";
    }
}

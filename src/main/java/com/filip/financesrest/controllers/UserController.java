package com.filip.financesrest.controllers;


import com.filip.financesrest.components.UserValidator;
import com.filip.financesrest.models.User;
import com.filip.financesrest.services.SecurityService;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController
{
	private UserService userService;

	private SecurityService securityService;

	private UserValidator userValidator;

	@Autowired
	public UserController(UserService userService, SecurityService securityService, UserValidator userValidator)
	{
		this.userService = userService;
		this.securityService = securityService;
		this.userValidator = userValidator;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model)
	{
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model)
	{
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors())
		{
			return "registration";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/";
	}

}

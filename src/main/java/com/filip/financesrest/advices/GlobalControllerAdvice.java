package com.filip.financesrest.advices;

import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice
{

	@Autowired
	CategoryRepository categoryRepository;


	@ModelAttribute("categories")
	public List<EntryCategory> populateCategoryList()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated())
		{
			List<EntryCategory> categories = categoryRepository.findByUser_Username(authentication.getName());
			if (categories != null)
				return categories;
		}
		return new ArrayList<EntryCategory>();

	}
}

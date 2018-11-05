package com.filip.financesrest.service_tests;


import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.CategoryRepository;
import com.filip.financesrest.services.CategoryService;
import com.filip.financesrest.services.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest
{
	@Mock
	private CategoryRepository categoryRepository;

	private CategoryService categoryService;

	@Before
	public void setUp()
	{
		categoryService = new CategoryServiceImpl(categoryRepository);
	}

	@Test
	public void findOne_returnsCategory()
	{
		given(categoryRepository.findOne(new Long(1))).willReturn(new EntryCategory("Test", new User(), new ArrayList<>()));

		EntryCategory category = categoryService.findOne((long) 1);

		assertThat(category.getName()).isEqualTo("Test");
	}
}

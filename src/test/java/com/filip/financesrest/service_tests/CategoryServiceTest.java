package com.filip.financesrest.service_tests;


import com.filip.financesrest.repositories.CategoryRepository;
import com.filip.financesrest.services.CategoryService;
import com.filip.financesrest.services.CategoryServiceImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

}

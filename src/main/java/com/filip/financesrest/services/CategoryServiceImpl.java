package com.filip.financesrest.services;

import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void save(EntryCategory category)
    {
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id)
    {
        categoryRepository.delete(id);
    }

    @Override
    public EntryCategory findOne(Long id)
    {
        return categoryRepository.findOne(id);
    }

    @Override
    public List<EntryCategory> findAll()
    {
        return categoryRepository.findAll();
    }

    @Override
    public List<EntryCategory> findByUser_Username(String username)
    {
        return categoryRepository.findByUser_Username(username);
    }

    @Override
    public List<EntryCategory> findByUser_UsernameOrderByName(String username)
    {
        return categoryRepository.findByUser_UsernameOrderByName(username);
    }

    @Override
    public boolean isOwner(Authentication authentication, Long id)
    {
        EntryCategory category = categoryRepository.findOne(id);
        return category.getUser().getUsername().equals(authentication.getName());
    }

    @Override
    public boolean exists(Long id)
    {
        EntryCategory category = categoryRepository.findOne(id);
        return category != null;
    }
}

package com.filip.financesrest.services;

import com.filip.financesrest.models.EntryCategory;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CategoryService
{
	void save(EntryCategory category);

	void delete(Long id);

	EntryCategory findOne(Long id);

	List<EntryCategory> findAll();

	List<EntryCategory> findByUser_Username(String username);

	List<EntryCategory> findByUser_UsernameOrderByName(String username);

	boolean isOwner(Authentication authentication, Long id);

	boolean exists(Long id);
}

package com.filip.financesrest.services;

import com.filip.financesrest.models.User;

public interface UserService
{
    void save(User user);
    User findByUsername(String username);
}

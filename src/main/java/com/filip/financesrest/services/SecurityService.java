package com.filip.financesrest.services;

public interface SecurityService
{
    String findLoggedInUsername();
    void autoLogin(String username, String password);

}

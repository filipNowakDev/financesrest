package com.filip.financesrest.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SecurityController
{
    @RequestMapping(value = "/api/username", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getCurrentUserName(Principal principal)
    {
        return "{\"username\":\"" + principal.getName() + "\"}";
    }
}

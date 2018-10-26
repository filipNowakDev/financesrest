package com.filip.financesrest;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index(Model model){
        return "redirect:/entries";
    }

    @RequestMapping("/login")
    public String login(Model model)
    {
        return "login";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied(Model model)
    {
        return "accessDenied";
    }
}

package com.mingto.work;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/hello")
public class HelloController {
    @RequestMapping(value="/welcome")
    public String printWelcome(ModelMap model) {
        System.out.println("gogo-----");
        model.addAttribute("msg", "Spring 3 MVC Hello World");
        return "hello";
    }
}

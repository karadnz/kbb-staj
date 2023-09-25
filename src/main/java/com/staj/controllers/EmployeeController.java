package com.staj.controllers;


import com.staj.Utilities.SessionUtils;
import com.staj.dto.PersonDtoAdd;
import com.staj.dto.PersonDtoAuth;
import com.staj.entities.Role;
import com.staj.services.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    final RestTemplate restTemplate;
    //final ApiService apiService;

    @GetMapping("/addUser")
    public String showAddUserForm2(Model model, HttpSession session)
    {
        if (!SessionUtils.isAuthenticated(session))
            return "redirect:/login";

        model.addAttribute("currentUser", (String) session.getAttribute("currentUser"));

        model.addAttribute("roles", ApiService.listRoles(session, restTemplate));
        model.addAttribute("personDtoAdd", new PersonDtoAdd());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser2(@Valid @ModelAttribute("personDtoAdd") PersonDtoAdd personDtoAdd,
                           BindingResult bindingResult, Model model, HttpSession session)
    {
        if (!ApiService.addUser(personDtoAdd, bindingResult, model, session, restTemplate))
            return "addUser";
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(@Valid PersonDtoAuth userLogin, BindingResult bindingResult, Model model, HttpSession session)
    {
        if (!ApiService.loginUser(userLogin, bindingResult, model, session, restTemplate))
            return "login";
        return "redirect:/dashboard";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("personDtoAdd") PersonDtoAdd personDtoAdd,
                               BindingResult bindingResult, Model model)
    {
        if (!ApiService.registerUser(personDtoAdd, bindingResult, model, restTemplate))
            return "register";
        return "redirect:/login";
    }

}






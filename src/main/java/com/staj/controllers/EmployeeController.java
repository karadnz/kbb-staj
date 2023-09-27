package com.staj.controllers;


import com.staj.Utilities.SessionUtils;
import com.staj.dto.PersonDtoAdd;
import com.staj.dto.PersonDtoAuth;
import com.staj.dto.PersonDtoEdit;
import com.staj.entities.Person;
import com.staj.entities.Role;
import com.staj.services.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/editUser/{userId}")
    public String showEditUserPage(@PathVariable Long userId, Model model, HttpSession session)
    {
        if (!SessionUtils.isAuthenticated(session))
            return "redirect:/login";
        // Retrieve user details
        /*Person user = personService.findPersonById(userId);

        if (user == null) {
            // Handle the case where no user with the provided userId is found
            // This can be a redirect, error message, etc.
            return "redirect:/error";
        }

        model.addAttribute("user", user);

        // If you have roles to display on the page, add them to the model as well.
        // List<Role> roles = roleService.getAllRoles();
        // model.addAttribute("roles", roles);*/

        model.addAttribute("currentUser", (String) session.getAttribute("currentUser"));
        model.addAttribute("idToEdit", userId);

        model.addAttribute("roles", ApiService.listRoles(session, restTemplate));
        model.addAttribute("personDtoAdd", new PersonDtoEdit());

        return "editUser";  // This refers to the name of your JSP file, "editUser.jsp".
    }

    @PostMapping("/editUser/{userId}")
    public String editUser(@PathVariable Long userId,
                           @Valid @ModelAttribute("personDtoEdit") PersonDtoEdit personDtoEdit,
                           BindingResult bindingResult,
                           Model model,
                           HttpSession session) {

        if (!ApiService.editUser(userId, personDtoEdit, bindingResult, model, session, restTemplate)) {
            return "editUser";  // Assuming you have a JSP page named "editUser.jsp"
        }
        return "redirect:/dashboard";
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






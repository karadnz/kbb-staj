package com.staj.controllers;


import com.staj.dto.PersonDtoAdd;
import com.staj.dto.PersonDtoAuth;
import com.staj.entities.Role;
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
public class EmployeeController
{

    final RestTemplate restTemplate;

    // Show add user form
    @GetMapping("/addUser")
    public String showAddUserForm(Model model, HttpSession session)
    {
        //user dropdown logic
        String token = (String) session.getAttribute("token");
        if (token == null)
            return "redirect:/login";
        String currentUser = (String) session.getAttribute("currentUser");
        if (currentUser != null)
            model.addAttribute("currentUser", currentUser);

        //get roles and display
        List<Role> roles = restTemplate.getForObject("http://localhost:8092/utils/listRoles", List.class);
        model.addAttribute("roles", roles);
        model.addAttribute("personDtoAdd", new PersonDtoAdd());  // Assuming you have a PersonDto for the form
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("personDtoAdd") PersonDtoAdd personDtoAdd,
                          BindingResult bindingResult, Model model, HttpSession session)
    {
        if (bindingResult.hasErrors()) {
            return "addUser";
        }

        // Get token from session
        String token = (String) session.getAttribute("token");
        if (token == null) {
            // Not logged in, redirect to login page
            model.addAttribute("errorMessage", "Not logged in. Please login to add a user.");
            return "redirect:/login";
        }

        // Create payload from form data
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", personDtoAdd.getUsername());
        payload.put("password", personDtoAdd.getPassword());
        payload.put("enable", true);  // Assuming default is true
        payload.put("email", personDtoAdd.getEmail());
        payload.put("name", personDtoAdd.getName());
        payload.put("s_id", personDtoAdd.getS_id());

        // Construct roles list
        List<Map<String, Long>> rolesList = new ArrayList<>();
        for (Long rid : personDtoAdd.getRoleRids()) {
            Map<String, Long> roleMap = new HashMap<>();
            roleMap.put("rid", rid);
            rolesList.add(roleMap);
        }
        payload.put("roles", rolesList);

        // Setting up headers with token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        // Call API to add user
        try {
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8092/addUser", HttpMethod.POST, entity, String.class);
            // Check response if necessary and redirect or show a success message
            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding user. Please try again.");
            return "addUser";
        }
    }


    @PostMapping("/login")
    public String login(@Valid PersonDtoAuth userLogin, BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "login";
        }

        // Define login payload
        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", userLogin.getUsername());
        loginPayload.put("password", userLogin.getPassword());

        // Call API to generate token
        ResponseEntity<String> response;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForEntity("http://localhost:8092/generateToken", loginPayload, String.class);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }

        // Assuming token is directly returned as a String
        String token = response.getBody();


        if (token == null || token.isEmpty()) {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }

        // Store token and username in session
        session.setAttribute("token", token);
        session.setAttribute("currentUser", userLogin.getUsername());

        // Redirect to dashboard
        return "redirect:/dashboard";
    }


    @PostMapping("/Simplelogin")
    public String simplelogin(String username, String password, HttpSession session) {
        // Define login payload
        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", username);
        loginPayload.put("password", password);

        // Call API to generate token
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8092/utils/generateToken", loginPayload, String.class);

        // Assuming token is directly returned as a String
        String token = response.getBody();

        // Store token in session
        session.setAttribute("token", token);

        // Redirect to dashboard
        return "redirect:/dashboard";
    }


    @GetMapping("/register")
    public String login()
    {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid PersonDtoAuth userRegistration, BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "register";  // Assuming your JSP file is named "register.jsp"
        }

        // Define registration payload
        Map<String, String> registrationPayload = new HashMap<>();
        registrationPayload.put("username", userRegistration.getUsername());
        registrationPayload.put("password", userRegistration.getPassword());

        // Call API to create a new user
        ResponseEntity<String> response;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForEntity("http://localhost:8092/register", registrationPayload, String.class);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error during registration. Please try again.");
            return "register";
        }

        // Assuming a success message or token is directly returned as a String
        String result = response.getBody();

        if (result == null || result.isEmpty())
        {
            model.addAttribute("errorMessage", "Registration failed. Please try again.");
            return "register";
        }

        // Assuming we want to log the user in directly after registration
        session.setAttribute("currentUser", userRegistration.getUsername());

        // Redirect to dashboard or login page after successful registration
        return "redirect:/dashboard";
    }
}





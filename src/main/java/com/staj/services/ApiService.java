package com.staj.services;

import com.staj.Utilities.SessionUtils;
import com.staj.dto.PersonDtoAdd;
import com.staj.dto.PersonDtoAuth;
import com.staj.dto.PersonDtoEdit;
import com.staj.entities.Person;
import com.staj.entities.Role;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    public static boolean addUser(PersonDtoAdd personDtoAdd, BindingResult bindingResult,
                                  Model model, HttpSession session, RestTemplate restTemplate)
    {
        if (bindingResult.hasErrors() || !SessionUtils.isAuthenticated(session))
            return false;

        // Create payload from form data
        Map<String, Object> payload = getStringObjectMap(personDtoAdd);

        // Setting up headers with token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + session.getAttribute("token"));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        // Call API to add user
        try {
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8092/addUser", HttpMethod.POST, entity, String.class);
            // Check response if necessary and redirect or show a success message
            return true;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding user. Please try again.");

            List<Role> roles = restTemplate.getForObject("http://localhost:8092/utils/listRoles", List.class);
            model.addAttribute("roles", roles);
            model.addAttribute("personDtoAdd", new PersonDtoAdd());
            return false;
        }
    }

    private static Map<String, Object> getStringObjectMap(PersonDtoAdd personDtoAdd) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", personDtoAdd.getUsername());
        payload.put("password", personDtoAdd.getPassword());
        payload.put("enable", true);  // Assuming default is true
        payload.put("email", personDtoAdd.getEmail());
        payload.put("name", personDtoAdd.getName());
        payload.put("s_id", personDtoAdd.getS_id());


        // Construct roles list
        List<Map<String, Long>> rolesList = new ArrayList<>();
        for (Long rid : personDtoAdd.getRoleRids())
        {
            Map<String, Long> roleMap = new HashMap<>();
            roleMap.put("rid", rid);
            rolesList.add(roleMap);
        }
        payload.put("roles", rolesList);
        return payload;
    }

    public static boolean loginUser(@Valid PersonDtoAuth userLogin, BindingResult bindingResult,
                                    Model model, HttpSession session, RestTemplate restTemplate)
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return false;
        }

        // Define login payload
        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", userLogin.getUsername());
        loginPayload.put("password", userLogin.getPassword());

        // Call API to generate token
        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity("http://localhost:8092/generateToken", loginPayload, String.class);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid username or password");
            return false;
        }

        // Assuming token is directly returned as a String
        String token = response.getBody();

        if (token == null || token.isEmpty())
        {
            model.addAttribute("errorMessage", "Invalid username or password");
            return false;
        }

        // Store token and username in session
        session.setAttribute("token", token);
        session.setAttribute("currentUser", userLogin.getUsername());

        return true;
    }

    public static boolean registerUser(PersonDtoAdd personDtoAdd, BindingResult bindingResult,
                                       Model model, RestTemplate restTemplate)
    {
        if (bindingResult.hasErrors())
            return false;

        // Create payload from form data
        HttpEntity<Map<String, Object>> entity = getMapHttpEntity(personDtoAdd);

        // Call API to register user
        try {
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8092/register", HttpMethod.POST, entity, String.class);
            // Check response if necessary and redirect or show a success message
            return true;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error registering. Please try again.");
            model.addAttribute("personDtoAdd", new PersonDtoAdd());
            return false;
        }
    }

    private static HttpEntity<Map<String, Object>> getMapHttpEntity(PersonDtoAdd personDtoAdd) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", personDtoAdd.getUsername());
        payload.put("password", personDtoAdd.getPassword());
        payload.put("enable", true);  // Assuming default is true
        payload.put("email", personDtoAdd.getEmail());
        payload.put("name", personDtoAdd.getName());
        payload.put("s_id", personDtoAdd.getS_id());

        // Construct HttpEntity object
        return new HttpEntity<>(payload);
    }

    public static List<Role> listRoles(HttpSession session, RestTemplate restTemplate) {
        // Logic to list roles
        return restTemplate.getForObject("http://localhost:8092/utils/listRoles", List.class);
    }

    public static List<Person> getPersons(HttpSession session, RestTemplate restTemplate) {
        // Logic to get persons
        //dete
        return restTemplate.getForObject("http://localhost:8092/utils/listRoles", List.class);
    }

    public static boolean deleteUser(String id, HttpSession session, RestTemplate restTemplate) {
        // Logic to delete user
        // Return true or false based on success
        return false;
    }



    public static boolean editUser(Long userId, PersonDtoEdit personDtoEdit, BindingResult bindingResult,
                                   Model model, HttpSession session, RestTemplate restTemplate)
    {
        if (bindingResult.hasErrors() || !SessionUtils.isAuthenticated(session))
            return false;

        // Create payload from form data
        Map<String, Object> payload = getStringObjectMapEdit(personDtoEdit);

        // Setting up headers with token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + session.getAttribute("token"));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        String uri = "http://localhost:8092/editUser/" + userId;

        // Call API to add user
        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
            // Check response if necessary and redirect or show a success message
            return true;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding user. Please try again.");

            List<Role> roles = restTemplate.getForObject("http://localhost:8092/utils/listRoles", List.class);
            model.addAttribute("roles", roles);
            model.addAttribute("personDtoAdd", new PersonDtoAdd());
            return false;
        }
    }

    private static Map<String, Object> getStringObjectMapEdit(PersonDtoEdit PersonDtoEdit) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", PersonDtoEdit.getUsername());
        payload.put("email", PersonDtoEdit.getEmail());
        payload.put("name", PersonDtoEdit.getName());
        payload.put("s_id", PersonDtoEdit.getS_id());


        // Construct roles list
        List<Map<String, Long>> rolesList = new ArrayList<>();
        for (Long rid : PersonDtoEdit.getRoleRids())
        {
            Map<String, Long> roleMap = new HashMap<>();
            roleMap.put("rid", rid);
            rolesList.add(roleMap);
        }
        payload.put("roles", rolesList);
        return payload;
    }
}

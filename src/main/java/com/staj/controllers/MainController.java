package com.staj.controllers;

import com.staj.entities.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController
{


    final RestTemplate restTemplate;

    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("currentUser");
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session)
    {
        //user dropdown logic
        String token = (String) session.getAttribute("token");
        if (token == null)
            return "redirect:/login";
        // Get the current user from the session
        String currentUser = (String) session.getAttribute("currentUser");
        // Add to model
        if (currentUser != null)
            model.addAttribute("currentUser", currentUser);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Person[]> responseEntity = restTemplate.exchange("http://localhost:8092/utils/listCustomer", HttpMethod.GET, entity, Person[].class);

        Person[] personsArray = responseEntity.getBody();
        List<Person> persons = Arrays.asList(personsArray != null ? personsArray : new Person[0]);
        model.addAttribute("Persons", persons);

        return "dashboard";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id, HttpSession session) {
        // Get token from session
        String token = (String) session.getAttribute("token");
        if (token == null)
            return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // Perform DELETE request to delete user
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8092/deleteUser/" + id,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        if (responseEntity.getStatusCodeValue() == 200) {
            // Handle success
            return "redirect:/dashboard";
        } else {
            // Handle failure
            return "errorPage"; // Replace with your error page
        }
    }

    @GetMapping("/userdetails/{userId}")
    public String userDetails(@PathVariable String userId, Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null)
            return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Person> responseEntity = restTemplate.exchange("http://localhost:8092/utils/userDetail/" + userId, HttpMethod.GET, entity, Person.class);

        Person person = responseEntity.getBody();
        model.addAttribute("person", person);

        return "userdetails";
    }



}

package com.myTutor.findMyTutor.controller;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myTutor.findMyTutor.entities.User;
import com.myTutor.findMyTutor.helpers.Helper;
import com.myTutor.findMyTutor.services.UserService;
import com.myTutor.findMyTutor.services.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService; 

    

   


    //user dashboard page
    @RequestMapping(value="/dashboard")
    public String userDashboard(Model model, Authentication authentication,Principal principal)
    {


        String userId = principal.getName(); // This fetches the ID from Principal (which is likely provider_user_id)

    // Attempt to fetch the user by provider_user_id
    Optional<User> userOptional = userService.getUserById(userId);
    
    if (userOptional.isPresent()) {
        User user = userOptional.get();
        model.addAttribute("userName", user.getName()); // Display user name
    } else {
        // Handle case where the user is not found by provider_user_id
        // For example, fetch user by another unique identifier like email
        // Or display a message if user is not found
        model.addAttribute("userName", "Unknown User");
        model.addAttribute("errorMessage", "User not found");
    }


        return "user/dashboard";
    }

    //user profile page 
    @RequestMapping(value="/profile")
    public String userProfile(Model model, Authentication authentication,Principal principal)
    {
    
     
       
    
         



    String userId = principal.getName(); // This fetches the ID from Principal (which is likely provider_user_id)

    // Attempt to fetch the user by provider_user_id
    Optional<User> userOptional = userService.getUserById(userId);
    
    if (userOptional.isPresent()) {
        User user = userOptional.get();
        model.addAttribute("userName", user.getName()); // Display user name
    } else {
        // Handle case where the user is not found by provider_user_id
        // For example, fetch user by another unique identifier like email
        // Or display a message if user is not found
        model.addAttribute("userName", "Unknown User");
        model.addAttribute("errorMessage", "User not found");
    }

     
        return "user/profile";
    }

    


     

    //user add contacts page

    //user view contacts

    //user edit contact

    //user delete contact
}

package com.myTutor.findMyTutor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.myTutor.findMyTutor.entities.User;
import com.myTutor.findMyTutor.helpers.Helper;
import com.myTutor.findMyTutor.services.UserService;

@ControllerAdvice
public class RootController {

    private Logger logger=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    



     @ModelAttribute()
    public void addLoggedInUserInformation(Model model,Authentication authentication)
    {
        if(authentication==null)
        {
            return;
        }
         

        System.out.println("Adding logged in user information to the model");
        String username=Helper.getEmailOfLoggedInUser(authentication);

        logger.info("User logged in:{}",username);

        //database to data fetching, getting user from db
        User user=userService.getUserByEmail(username);


         
            System.out.println(user.getName());
        System.out.println(user.getEmail());

        System.out.println("User profile");

        model.addAttribute("loggedInUser", user);

        

        

    }
    
}

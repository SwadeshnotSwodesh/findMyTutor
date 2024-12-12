package com.myTutor.findMyTutor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myTutor.findMyTutor.entities.User;
import com.myTutor.findMyTutor.forms.UserForm;
import com.myTutor.findMyTutor.helpers.Message;
import com.myTutor.findMyTutor.helpers.MessageType;
import com.myTutor.findMyTutor.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {


    @Autowired
    private UserService userService;

    // @GetMapping("/")
    // public String index()
    // {
    //     return "redirect:/home";
    // }


    @RequestMapping("/home")
    public String home(Model model)
    {
        System.out.println("Home page handler");
        //sending data to view
        model.addAttribute("Fname", "Swadesh");
        model.addAttribute("Lname", "Thapa");
        model.addAttribute("github", "https://github.com/SwadeshnotSwodesh");

        return "home";
    }


    @RequestMapping("/about")
    public String aboutPage(Model model)
    {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading...");
        return "about";
    }


    @RequestMapping("/services")
    public String servicesPage()
    {
        System.out.println("Services page loading...");
        return "services";
    }


    @GetMapping("/contact")
    public String contact()
    {
        return new String("contact");
    }


    //this is for the login
    @GetMapping("/login")
    public String login()
    {
        return new String("login");
    }
    

    //this is for the new registration
    @GetMapping("/register")
    public String register(Model model)
    {
        UserForm userForm=new UserForm();
        // userForm.setName("Swadesh");
        // userForm.setAbout("Swadesh is a good person");
        model.addAttribute("userForm", userForm);
       

        return "register";
    }



    



    //this is for the registration processing...
    @RequestMapping(value="/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute("userForm") UserForm userForm,BindingResult rBindingResult , HttpSession session)
    {
        System.out.println("Processing registration");
        //fetch form data
        //UserForm
        System.out.println(userForm);
        
        //validate form data
        if(rBindingResult.hasErrors())
        {
            return "register";
        }
         
        
        //save to database


        //userservice
        //Userform-->User
    //     User user=User.builder()
    //         .name(userForm.getName())
    //         .email(userForm.getEmail())
    //         .password(userForm.getPassword())
    //         .about(userForm.getAbout())
    //         .phoneNumber(userForm.getPhoneNumber())
    //    // .profilePic(profilePic:"")
    //         .build();


        User user=new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("");


        
            User savedUser=userService.saveUser(user);
            System.out.println("User saved...");
        
        //message="Registration Successful"

        //add the message:
        Message message=Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message",message);
        //redirect to login page
        return "redirect:/register";
    }


 





 
    




    @GetMapping("/")
public String index(HttpSession session) {
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser != null) {
        return "redirect:/user/dashboard"; // Redirect to dashboard if logged in
    }
    return "redirect:/home"; // Redirect to home otherwise
}




 

}

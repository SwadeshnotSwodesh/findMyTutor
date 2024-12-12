package com.myTutor.findMyTutor.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myTutor.findMyTutor.entities.User;
import com.myTutor.findMyTutor.forms.UserForm;
import com.myTutor.findMyTutor.helpers.AppConstants;
import com.myTutor.findMyTutor.helpers.ResourceNotFoundException;
import com.myTutor.findMyTutor.repositories.UserRepo;
import com.myTutor.findMyTutor.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
private UserRepo userRepo;

@Autowired
private PasswordEncoder passwordEncoder;

private Logger logger=LoggerFactory.getLogger(this.getClass()); 

    @Override
    public User saveUser(User user) {
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);
        //password encode
        //user.setPassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));


        logger.info(user.getProvider().toString());


         return userRepo.save(user);
    }

    // @Override
    // public Optional<User> getUserById(String id) {
    //     //String userId=UUID.randomUUID().toString();

    //    // return Optional.ofNullable(userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
    

    //       return userRepo.findById(id);
    // }

    @Override
    public Optional<User> updateUser(User user) {
         User user2=userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
         //updating user2
         user2.setName(user.getName());
         user2.setEmail(user.getEmail());
         user2.setPassword(user.getPassword());
         user2.setAbout(user.getAbout());
         user2.setPhoneNumber(user.getPhoneNumber());
         user2.setProfilePic(user.getProfilePic());
         user2.setEnabled(user.isEnabled());
         user2.setEmailVerified(user.isEmailVerified());
         user2.setPhoneVerified((user.isPhoneVerified()));
         user2.setProvider(user.getProvider());
         user2.setProviderUserId(user.getProviderUserId());


         //saving the user in database
         User save=userRepo.save(user2);

         return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
         User user2 = userRepo.findById(userId).orElse(null);
         return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
         User user=userRepo.findByEmail(email).orElse(null);
         return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
         return userRepo.findAll();
    }

    @Override
    public User deletUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletUser'");
    }

    @Override
    public User saveUser(UserForm userForm) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveUser'");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }


    @Override
public Optional<User> getUserById(String id) {
    // First try to find user by provider_user_id
    Optional<User> user = userRepo.findByProviderUserId(id);
    if (user.isEmpty()) {
        // If not found, fall back to finding by email or username
        user = userRepo.findByEmail(id); // Or some other field like username
    }
    return user;
}


     

    

     
    
}

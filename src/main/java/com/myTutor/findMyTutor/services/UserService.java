package com.myTutor.findMyTutor.services;

import java.util.List;
import java.util.Optional;

import com.myTutor.findMyTutor.entities.User;
import com.myTutor.findMyTutor.forms.UserForm;

public interface UserService {
    
    User saveUser(User user);

    User saveUser(UserForm userForm);


    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    User deletUser(User user);

    void deleteUser(String id);


    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<User>getAllUsers();

    User getUserByEmail(String email);

    // User getUserByName(String name);
}
 
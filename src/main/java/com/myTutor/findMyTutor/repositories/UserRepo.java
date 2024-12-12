package com.myTutor.findMyTutor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myTutor.findMyTutor.entities.User;

//repositories is for database interaction
@Repository
public interface UserRepo extends JpaRepository<User, String>{
    //extra methods db related for db operations
    //custom query methods
    //custom finder methods
    
    Optional<User>findByEmail(String email);

    Optional<User>findByEmailAndPassword(String email,String password);

    Optional<User>findByName(String name);

    Optional<User> findByProviderUserId(String id);
}

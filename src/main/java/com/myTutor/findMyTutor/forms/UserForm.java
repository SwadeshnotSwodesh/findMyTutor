package com.myTutor.findMyTutor.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, message = "Name must have at least 3 characters")
    private String name;
    
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email address")
    private String email;
    
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
    
    @NotBlank(message = "Please provide a description about yourself")
    private String about;
    
    @Pattern(regexp = "\\d{8,15}", message = "Phone number must contain 8-15 digits")
    private String phoneNumber;
    

     

}

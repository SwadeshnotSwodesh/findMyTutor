package com.myTutor.findMyTutor.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.myTutor.findMyTutor.services.impl.SecurityCustomUserDetailService;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 

@Configuration
public class SecurityConfig {
 
    //user create and login using java code with in memory service
     
    // @Bean
    // public UserDetailsService userDetailsService()
    // {
    //    UserDetails user1= User
    //    .withDefaultPasswordEncoder()
    //    .username("admin123")
    //    .password("admin123")
    //    .roles("ADMIN","USER")
    //    .build();

    //    UserDetails user2=User
    //     .withDefaultPasswordEncoder()
    //     .username("user123")
    //     .password("password")
    //     //.roles(null)
    //     .build();

    //     var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager;
    // }
    
    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthenticationSuccessHandler handler;

    //configuration of authentication provider for spring security



    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        //user details service's object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //password encoder's object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());


        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {

        //configuration
        
        //here we have fixed which one remains public and which one remains private
        httpSecurity.authorizeHttpRequests(authorize->{
            //authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //form default login
        //if anything to change then we'll come here regarding form related login
        
        //this gives by default login modules
       // httpSecurity.formLogin(Customizer.withDefaults());

       httpSecurity.formLogin(formLogin->{

        //
        formLogin.loginPage("/login");
        formLogin.loginProcessingUrl("/authenticate");
        formLogin.successForwardUrl("/user/dashboard");
        //this throws error in the browser 
        // formLogin.failureForwardUrl("/login?error=true");
        formLogin.usernameParameter("email");
        formLogin.passwordParameter("password");
        // formLogin.failureHandler(new AuthenticationFailureHandler() {

        //     @Override
        //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        //             AuthenticationException exception) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
        //     }
            
        // });
        // formLogin.successHandler(new AuthenticationSuccessHandler() {

        //     @Override
        //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        //             Authentication authentication) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
        //     }
            
        // });

       });

       httpSecurity.csrf(AbstractHttpConfigurer::disable); 
       httpSecurity.logout(logoutForm->{
        logoutForm.logoutUrl("/do-logout");
        logoutForm.logoutSuccessUrl("/login?logout=true");
       });

       //oauth configurations
       //this is for the default login
       //httpSecurity.oauth2Login(Customizer.withDefaults());
       httpSecurity.oauth2Login(oauth->{
        oauth.loginPage("/login"); 
        oauth.successHandler(handler);
       }

       );



        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}

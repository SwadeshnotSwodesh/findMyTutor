package com.myTutor.findMyTutor.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
         
        // If the user is logged in using OAuth2 (e.g., Google, GitHub)
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String clientId = oauthToken.getAuthorizedClientRegistrationId();

            String username="";

            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting email from Google");
                username=oauth2User.getAttributes().get("email").toString();
            } else if (clientId.equalsIgnoreCase("github")) {
                System.out.println("Getting email from GitHub");
                username=oauth2User.getAttribute("email") !=null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString()+"@github.com";
            }
            return username;
        }

        // For self-provided authentication (e.g., username and password)
        System.out.println("Getting data from self-provider");
        return authentication.getName();
    }
}

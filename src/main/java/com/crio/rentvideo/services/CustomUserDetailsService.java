package com.crio.rentvideo.services;

import com.crio.rentvideo.entity.User;
import com.crio.rentvideo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections; // For simple role collection

// CustomUserDetailsService provides data about users. SecurityConfig provides the configuration and rules 
//for the entire security system


@Service // @component
public class CustomUserDetailsService implements UserDetailsService { 

    @Autowired // Injecting UserRepository
    private UserRepository userRepository;

    /**
     * Locates the user based on the username (email in this case).
     * This method is used by Spring Security during the authentication process.
     
     */

    @Override // userdetail  is also interface provide by spring security.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by email from our UserRepository
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Build and return Spring Security's UserDetails object
        // The password is the hashed password from the database.
        // The role is converted into a Spring Security GrantedAuthority.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}

/*
No, the CustomUserDetailsService is not related to handling many-to-many relationships between entities.
 Its purpose is entirely different.The CustomUserDetailsService is a crucial component in Spring Security's authentication process. Its sole responsibility 
is to Load User Details:
 When a user attempts to log in (e.g., using Basic Auth with their email and password), 
Spring Security needs to verify those credentials. To do this, it calls the loadUserByUsername() method of your 
UserDetailsService implementation.Provide Spring Security's UserDetails Object: Your loadUserByUsername() method retrieves 
the user from your database (using your UserRepository) and then converts that user's information (email, hashed password, 
and roles) into a org.springframework.security.core.userdetails.UserDetails object. This is a contract that Spring Security 
understands. 
*/
package com.crio.rentvideo.services;

import com.crio.rentvideo.entity.User;
import com.crio.rentvideo.entity.Role.Role;
import com.crio.rentvideo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired; // Import for @Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

// Marks this class as a Spring Service component
@Service
public class UserService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private BCryptPasswordEncoder passwordEncoder; // Used for hashing passwords

    /**
     * Registers a new user.
     * @param user The user object containing registration details.
     * @return The saved User object.
     * @throws RuntimeException if a user with the given email already exists.
     */
    public User registerUser(User user) {
        // Check if a user with the given email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists.");
        }

        // Hash the password using BCrypt before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role to CUSTOMER if not specified
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }

        return userRepository.save(user); // Save the user to the database
    }

    /**
     * Finds a user by their email address.
     * @param email The email address of the user.
     * @return An Optional containing the User if found, empty otherwise.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
package com.crio.rentvideo.controller;


import com.crio.rentvideo.entity.User;
import com.crio.rentvideo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired; // For field injection
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth") // base path
public class AuthController {

    @Autowired 
    private UserService userService;

    /**
     * Endpoint for user registration.
     * This is a public endpoint as configured in SecurityConfig.
     * Maps to POST /api/auth/register
     *
     * @param user The User object containing registration details (email, password, firstName, lastName, role).
     * The password will be hashed by the UserService.
     * The role will default to CUSTOMER if not provided.
     * @return ResponseEntity with the registered User and HTTP status 201 (Created) on success,
     * or HTTP status 400 (Bad Request) if the email already exists.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            // Return 201 Created status
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle cases where email already exists
            // In a real app, you might return a more specific error DTO
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for user login confirmation.
     * This endpoint simply returns a success message for an authenticated user.
     * Basic Auth mechanism handles the actual authentication before this method is called.
     * Maps to GET /api/auth/login
     *
     * @return ResponseEntity with a success message and HTTP status 200 (OK) if authenticated.
     */
    @GetMapping("/login")
    public ResponseEntity<String> loginSuccess() {
        // If this endpoint is reached, it means Basic Authentication was successful.
        return new ResponseEntity<>("Login successful!", HttpStatus.OK);
    }
}

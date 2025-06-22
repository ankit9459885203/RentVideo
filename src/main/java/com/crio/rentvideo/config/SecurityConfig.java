package com.crio.rentvideo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Enable method-level security
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// ye vala sara vo architect digram hai, filter , manager ,proveder ,password encoder etc. but jumbled hai bs.

// CustomUserDetailsService provides data about users. SecurityConfig provides the configuration and rules 
//for the entire security system


@Configuration // tells that this class is the spring container
@EnableWebSecurity 
@EnableMethodSecurity(prePostEnabled = true) //method level security ,  Without this annotation, other method-level security 
//annotations (like @PreAuthorize, @PostAuthorize, @Secured, @RolesAllowed) would not work.
// pre vala check krta ki method execute krne se pehle dekh lo (if condition) ki bnda admin hai ya nhi , 
// post vala result return krne se pehle ek var condtion check krega.

public class SecurityConfig {

    // Removed direct @Autowired of CustomUserDetailsService here,
    // as it will be used implicitly by AuthenticationManager below.
    // private CustomUserDetailsService customUserDetailsService;

    // Inject AuthenticationConfiguration to get the AuthenticationManager bean
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;


    /**
     * Configures the BCryptPasswordEncoder as a Spring bean.
     * This encoder is used for hashing passwords for storage and verification.
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   

    /**
     * Configures the security filter chain, defining security rules for HTTP requests.
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless REST APIs using Basic Auth
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/**").permitAll() // Allow unauthenticated access to /api/auth/** (e.g., registration, login)
                .requestMatchers("/api/videos/admin/**").hasRole("ADMIN") // ADMIN can manage videos
                .requestMatchers("/api/videos/**").authenticated() // All other video endpoints require authentication
                .anyRequest().authenticated() // Any other request not matched requires authentication
            )
            .httpBasic(org.springframework.security.config.Customizer.withDefaults()) // Enable Basic Authentication
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configure stateless sessions for REST APIs

        return http.build();
    }
}

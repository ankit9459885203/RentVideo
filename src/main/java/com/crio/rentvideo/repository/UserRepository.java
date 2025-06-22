package com.crio.rentvideo.repository;


import com.crio.rentvideo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Marks this interface as a Spring Data JPA repository
@Repository
// Extends JpaRepository, providing CRUD operations for the User entity
// <User, Long> specifies the Entity type (User) and the type of its Primary Key (Long)
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method: Spring Data JPA will automatically implement this
    // Finds a User by their email address. Useful for login and checking unique emails.
    Optional<User> findByEmail(String email);
}

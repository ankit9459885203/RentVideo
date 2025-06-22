package com.crio.rentvideo.entity;

import com.crio.rentvideo.entity.Role.Role;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;




@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor 
@AllArgsConstructor 

@Entity // Marks this class as a JPA entity, mapping to a database table
@Table(name = "users") // Specifies the table name in the database
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures auto-increment for the ID
    private Long id;

    @Column(unique = true, nullable = false) // Specifies column properties: must be unique and not null
    private String email;

    @Column(nullable = false) // Password cannot be null
    private String password; // This will store the BCrypt hashed password

    @Column(name = "first_name") // Custom column name for the database
    private String firstName;

    @Column(name = "last_name") // Custom column name for the database
    private String lastName;

    @Enumerated(EnumType.STRING) // Stores the enum name as a String in the database
    @Column(nullable = false) // Role cannot be null
    private Role role; // Enum for user roles (CUSTOMER, ADMIN)


   
}
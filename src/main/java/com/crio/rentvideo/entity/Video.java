package com.crio.rentvideo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor 
@AllArgsConstructor 
@Entity // Marks this class as a JPA entity
@Table(name = "videos") // Specifies the table name in the database
public class Video {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private String title;

    @Column(nullable = false) 
    private String director;

    @Column(nullable = false) 
    private String genre;

    
     @Column(nullable = false)
    private boolean availabilityStatus;
    }


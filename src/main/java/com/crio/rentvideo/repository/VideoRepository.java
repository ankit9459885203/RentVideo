package com.crio.rentvideo.repository;


import com.crio.rentvideo.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Marks this interface as a Spring Data JPA repository
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    // Custom query method: 
    List<Video> findByAvailabilityStatusTrue();
}

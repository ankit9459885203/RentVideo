package com.crio.rentvideo.controller;



import com.crio.rentvideo.entity.Video;
import com.crio.rentvideo.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired; // For field injection
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import for @PreAuthorize
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController

@RequestMapping("/api/videos")
public class VideoController {

    @Autowired 
    private VideoService videoService;

    /**
     * Endpoint to retrieve all available videos.
     * This endpoint is accessible to any authenticated user (CUSTOMER or ADMIN).
     * Maps to GET /api/videos
     *
     * @return ResponseEntity with a list of available videos and HTTP status 200 (OK).
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()") // Explicitly allows any authenticated user (CUSTOMER or ADMIN)
    public ResponseEntity<List<Video>> getAllAvailableVideos() {
        List<Video> videos = videoService.getAllAvailableVideos();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a specific video by its ID.
     * This endpoint is accessible to any authenticated user (CUSTOMER or ADMIN).
     * Maps to GET /api/videos/{id}
     *
     * @param id The ID of the video to retrieve.
     * @return ResponseEntity with the Video object and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the video does not exist.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Allows any authenticated user
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Optional<Video> video = videoService.getVideoById(id);
        return video.map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to create a new video.
     * This endpoint is only accessible to users with the 'ADMIN' role.
     * Maps to POST /api/videos/admin
     *
     * @param video The Video object to be created.
     * @return ResponseEntity with the created Video and HTTP status 201 (Created).
     */
    @PostMapping("/admin") // Note: This specific path will trigger the SecurityConfig rule for ADMIN
    @PreAuthorize("hasRole('ADMIN')") // Enforces ADMIN role at method level
    public ResponseEntity<Video> createVideo(@RequestBody Video video) {
        Video createdVideo = videoService.createVideo(video);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing video by its ID.
     * This endpoint is only accessible to users with the 'ADMIN' role.
     * Maps to PUT /api/videos/admin/{id}
     *
     * @param id The ID of the video to update.
     * @param videoDetails The Video object containing updated details.
     * @return ResponseEntity with the updated Video and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the video does not exist.
     */
    @PutMapping("/admin/{id}") // Note: This specific path will trigger the SecurityConfig rule for ADMIN
    @PreAuthorize("hasRole('ADMIN')") // Enforces ADMIN role at method level
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video videoDetails) {
        try {
            Video updatedVideo = videoService.updateVideo(id, videoDetails);
            return new ResponseEntity<>(updatedVideo, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Catches the "Video not found" exception from VideoService
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a video by its ID.
     * This endpoint is only accessible to users with the 'ADMIN' role.
     * Maps to DELETE /api/videos/admin/{id}
     *
     * @param id The ID of the video to delete.
     * @return ResponseEntity with HTTP status 204 (No Content) on successful deletion,
     * or HTTP status 404 (Not Found) if the video does not exist.
     */
    @DeleteMapping("/admin/{id}") // Note: This specific path will trigger the SecurityConfig rule for ADMIN
    @PreAuthorize("hasRole('ADMIN')") // Enforces ADMIN role at method level
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        try {
            videoService.deleteVideo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content for successful deletion
        } catch (RuntimeException e) {
            // Catches the "Video not found" exception from VideoService
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

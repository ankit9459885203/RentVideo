package com.crio.rentvideo.services;
import com.crio.rentvideo.entity.Video;
import com.crio.rentvideo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired; // Import for @Autowired
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Marks this class as a Spring Service component
@Service
public class VideoService {

    @Autowired 
    private VideoRepository videoRepository;

   
    public List<Video> getAllAvailableVideos() {
        return videoRepository.findByAvailabilityStatusTrue();
    }

    
    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);   // return An Optional containing the Video if found, empty otherwise.
    }

   
    public Video createVideo(Video video) {
        // Ensure availability status is true for newly created videos as per requirements
        video.setAvailabilityStatus(true);
        return videoRepository.save(video);
    }

    /**
     * Updates an existing video.
     * This method is intended to be called by ADMIN users only.
     */
    public Video updateVideo(Long id, Video videoDetails) {
        // Find the existing video by ID
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));

        // Update fields (excluding ID)
        existingVideo.setTitle(videoDetails.getTitle());
        existingVideo.setDirector(videoDetails.getDirector());
        existingVideo.setGenre(videoDetails.getGenre());
        existingVideo.setAvailabilityStatus(videoDetails.isAvailabilityStatus()); // Update availability status

        return videoRepository.save(existingVideo); // Save the updated video
    }

    /**
     * Deletes a video by its ID.
     * This method is intended to be called by ADMIN users only.
     */
    public void deleteVideo(Long id) {
        // Check if the video exists before deleting
        if (!videoRepository.existsById(id)) {
            throw new RuntimeException("Video not found with id: " + id);
        }
        videoRepository.deleteById(id); 
    }
}

package com.ardkyer.rion.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;
import com.ardkyer.rion.entity.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VideoService {
    Video uploadVideo(Video video, MultipartFile file) throws IOException;
    S3Object getVideoFile(String fileName);
    Optional<Video> getVideoById(Long id);
    List<Video> getVideosByUser(User user);
    List<Video> getLikedVideosByUser(User user);
    List<Video> getTopVideos();
    Video updateVideo(Video video);
    void deleteVideo(Long id);
    List<Video> getAllVideos();
    void incrementViewCount(Long videoId);
}
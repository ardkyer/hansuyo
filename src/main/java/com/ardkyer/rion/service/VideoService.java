package com.ardkyer.rion.service;

import com.ardkyer.rion.entity.*;
import java.util.List;
import java.util.Optional;

public interface VideoService {
    Video uploadVideo(Video video);
    Optional<Video> getVideoById(Long id);
    List<Video> getVideosByUser(User user);
    List<Video> getLikedVideosByUser(User user);
    List<Video> getTopVideos();
    Video updateVideo(Video video);
    void deleteVideo(Long id);
    List<Video> getAllVideos();  // 새로 추가된 메소드
}
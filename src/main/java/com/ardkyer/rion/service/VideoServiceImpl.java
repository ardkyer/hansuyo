package com.ardkyer.rion.service;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    @Transactional
    public Video uploadVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    @Override
    public List<Video> getVideosByUser(User user) {
        return videoRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<Video> getTopVideos() {
        return videoRepository.findTop10ByOrderByViewCountDesc();
    }

    @Override
    @Transactional
    public Video updateVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
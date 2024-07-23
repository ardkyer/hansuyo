package com.ardkyer.rion.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final AmazonS3 amazonS3Client;

    @Value("ardkyerspring1")
    private String bucketName;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, AmazonS3 amazonS3Client) {
        this.videoRepository = videoRepository;
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    @Transactional
    public Video uploadVideo(Video video, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

        video.setVideoUrl(fileName);
        return videoRepository.save(video);
    }

    @Override
    public S3Object getVideoFile(String fileName) {
        return amazonS3Client.getObject(bucketName, fileName);
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
    public List<Video> getLikedVideosByUser(User user) {
        return videoRepository.findByLikesUser(user);
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
        Optional<Video> videoOptional = videoRepository.findById(id);
        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();

            // Delete the file from S3
            amazonS3Client.deleteObject(bucketName, video.getVideoUrl());

            // Remove the video from the database
            videoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Video not found with id: " + id);
        }
    }

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    // You might want to add this method to increment view count
    @Transactional
    public void incrementViewCount(Long videoId) {
        Optional<Video> videoOptional = videoRepository.findById(videoId);
        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            video.setViewCount(video.getViewCount() + 1);
            videoRepository.save(video);
        }
    }
}
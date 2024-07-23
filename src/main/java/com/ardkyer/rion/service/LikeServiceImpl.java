package com.ardkyer.rion.service;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    @Transactional
    public Like addLike(Like like) {
        return likeRepository.save(like);
    }

    @Override
    @Transactional
    public void removeLike(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public boolean hasUserLikedVideo(User user, Video video) {
        return likeRepository.existsByUserAndVideo(user, video);
    }

    @Override
    public long getLikeCountForVideo(Video video) {
        return likeRepository.countByVideo(video);
    }
}
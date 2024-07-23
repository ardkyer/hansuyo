package com.ardkyer.rion.service;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LikeService {
    Like addLike(Like like);
    void removeLike(Long id);
    boolean hasUserLikedVideo(User user, Video video);
    long getLikeCountForVideo(Video video);
}
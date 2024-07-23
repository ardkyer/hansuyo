package com.ardkyer.rion.service;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FollowService {
    Follow followUser(User follower, User followed);
    void unfollowUser(User follower, User followed);
    boolean isFollowing(User follower, User followed);
    List<User> getFollowers(User user);
    List<User> getFollowing(User user);
    long getFollowerCount(User user);
    long getFollowingCount(User user);
}
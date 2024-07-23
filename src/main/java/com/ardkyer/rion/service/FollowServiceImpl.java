package com.ardkyer.rion.service;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Follow followUser(User follower, User followed) {
        if (isFollowing(follower, followed)) {
            throw new IllegalStateException("Already following this user");
        }
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);
        return followRepository.save(follow);
    }

    @Override
    @Transactional
    public void unfollowUser(User follower, User followed) {
        followRepository.findByFollowerAndFollowed(follower, followed)
                .ifPresent(follow -> followRepository.delete(follow));
    }

    @Override
    public boolean isFollowing(User follower, User followed) {
        return followRepository.existsByFollowerAndFollowed(follower, followed);
    }

    @Override
    public List<User> getFollowers(User user) {
        return followRepository.findByFollowed(user).stream()
                .map(Follow::getFollower)
                .toList();
    }

    @Override
    public List<User> getFollowing(User user) {
        return followRepository.findByFollower(user).stream()
                .map(Follow::getFollowed)
                .toList();
    }

    @Override
    public long getFollowerCount(User user) {
        return followRepository.countByFollowed(user);
    }

    @Override
    public long getFollowingCount(User user) {
        return followRepository.countByFollower(user);
    }
}
package com.ardkyer.rion.controller;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<Follow> followUser(@RequestParam Long followerId, @RequestParam Long followedId) {
        User follower = new User();
        follower.setId(followerId);
        User followed = new User();
        followed.setId(followedId);
        Follow follow = followService.followUser(follower, followed);
        return new ResponseEntity<>(follow, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> unfollowUser(@RequestParam Long followerId, @RequestParam Long followedId) {
        User follower = new User();
        follower.setId(followerId);
        User followed = new User();
        followed.setId(followedId);
        followService.unfollowUser(follower, followed);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isFollowing(@RequestParam Long followerId, @RequestParam Long followedId) {
        User follower = new User();
        follower.setId(followerId);
        User followed = new User();
        followed.setId(followedId);
        boolean isFollowing = followService.isFollowing(follower, followed);
        return new ResponseEntity<>(isFollowing, HttpStatus.OK);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<User> followers = followService.getFollowers(user);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<User> following = followService.getFollowing(user);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }

    @GetMapping("/count/followers/{userId}")
    public ResponseEntity<Long> getFollowerCount(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        long followerCount = followService.getFollowerCount(user);
        return new ResponseEntity<>(followerCount, HttpStatus.OK);
    }

    @GetMapping("/count/following/{userId}")
    public ResponseEntity<Long> getFollowingCount(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        long followingCount = followService.getFollowingCount(user);
        return new ResponseEntity<>(followingCount, HttpStatus.OK);
    }
}
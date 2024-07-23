package com.ardkyer.rion.controller;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<Like> addLike(@RequestBody Like like) {
        Like addedLike = likeService.addLike(like);
        return new ResponseEntity<>(addedLike, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeLike(@PathVariable Long id) {
        likeService.removeLike(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserLikedVideo(@RequestParam Long userId, @RequestParam Long videoId) {
        User user = new User();
        user.setId(userId);
        Video video = new Video();
        video.setId(videoId);
        boolean hasLiked = likeService.hasUserLikedVideo(user, video);
        return new ResponseEntity<>(hasLiked, HttpStatus.OK);
    }

    @GetMapping("/count/{videoId}")
    public ResponseEntity<Long> getLikeCountForVideo(@PathVariable Long videoId) {
        Video video = new Video();
        video.setId(videoId);
        long likeCount = likeService.getLikeCountForVideo(video);
        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }
}
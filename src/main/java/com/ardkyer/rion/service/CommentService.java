package com.ardkyer.rion.service;

import com.ardkyer.rion.entity.*;
import com.ardkyer.rion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment addComment(Comment comment);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getCommentsByVideo(Video video);
    Comment updateComment(Comment comment);
    void deleteComment(Long id);
    long getCommentCountForVideo(Video video);
}

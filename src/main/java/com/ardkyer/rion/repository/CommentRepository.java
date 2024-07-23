package com.ardkyer.rion.repository;

import com.ardkyer.rion.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideoOrderByCreatedAtDesc(Video video);
    long countByVideo(Video video);
}

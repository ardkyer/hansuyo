package com.ardkyer.rion.repository;

import com.ardkyer.rion.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByUserOrderByCreatedAtDesc(User user);
    List<Video> findTop10ByOrderByViewCountDesc();
}

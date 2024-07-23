package com.ardkyer.rion.repository;

import com.ardkyer.rion.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);
    boolean existsByFollowerAndFollowed(User follower, User followed);
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowed(User followed);
    long countByFollower(User follower);
    long countByFollowed(User followed);
}
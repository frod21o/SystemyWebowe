package com.piisw.jpa.repositories;

import com.piisw.jpa.entities.Follower;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    @EntityGraph(value = "Follower.withCommentsAndEvents", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Follower> findByUserId(Long userId);
}
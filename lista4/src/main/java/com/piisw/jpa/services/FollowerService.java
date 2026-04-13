package com.piisw.jpa.services;

import com.piisw.jpa.entities.Follower;
import com.piisw.jpa.repositories.FollowerComment;
import com.piisw.jpa.repositories.FollowerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;

    public List<FollowerComment> getFollowerComments(Long userId) {

        Follower follower = followerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        return follower.getComments().stream()
                .map(c -> new FollowerComment(
//                        c.getEvent().getDescription(),
                        "",
                        c.getEvent().getTime(),
                        c.getEvent().isAnalysisRequired(),
                        c.getContent(),
                        follower.getSubscriptionDate()
                ))
                .toList();
    }
}

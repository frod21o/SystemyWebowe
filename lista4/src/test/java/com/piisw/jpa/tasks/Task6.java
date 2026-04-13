package com.piisw.jpa.tasks;

import com.piisw.jpa.entities.*;
import com.piisw.jpa.repositories.FollowerComment;
import com.piisw.jpa.repositories.FollowerRepository;
import com.piisw.jpa.services.FollowerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class Task6 {

    @Autowired
    private FollowerService followerService;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void shouldReturnFollowerCommentsForGivenUserId() {
        // given
        String serverName = "myServerName";
        String ip = "127.0.0.1";
        Server server = new Server(serverName, ip);

        Long userId = 1L;
        LocalDate subDate = LocalDate.now().minusDays(10);
        LocalDateTime eventTime = LocalDateTime.now().minusDays(1);
        boolean analysisRequired = true;
        String commentContent = "mock comment";

        SqlEvent event = new SqlEvent();
        event.setTime(eventTime);
        event.setAnalysisRequired(analysisRequired);
        event.setServer(server);
        event.setSqlQuery("");

        Follower follower = new Follower();
        follower.setUserId(userId);
        follower.setSubscriptionDate(subDate);
        Comment comment = new Comment();
        comment.setContent(commentContent);
        comment.setEvent(event);
        comment.setFollower(follower);
        follower.setComments(List.of(comment));

        testEntityManager.persist(server);
        testEntityManager.persist(event);
        testEntityManager.persist(follower);
        testEntityManager.persist(comment);
        testEntityManager.flush();

        // when
        List<FollowerComment> result = followerService.getFollowerComments(userId);

        // then
        assertThat(result, Matchers.hasSize(1));
        FollowerComment mappedEvent = result.getFirst();
        assertThat(mappedEvent.commentContent(), Matchers.is(commentContent));
        assertThat(mappedEvent.eventTime(), Matchers.is(eventTime));
        assertThat(mappedEvent.subscriptionDate(), Matchers.is(subDate));
        assertThat(mappedEvent.analysisRequired(), Matchers.is(analysisRequired));
    }

    @Test
    void shouldThrowExceptionWhenFollowerNotFound() {
        // given
        Long nonExistingUserId = 999L;

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            followerService.getFollowerComments(nonExistingUserId);
        });

        assertThat(exception.getMessage(), Matchers.is("Follower not found"));
    }

    @TestConfiguration
    static class FollowerServiceTestContextConfiguration {
        @Bean
        public FollowerService followerService(FollowerRepository followerRepository) {
            return new FollowerService(followerRepository);
        }
    }

}

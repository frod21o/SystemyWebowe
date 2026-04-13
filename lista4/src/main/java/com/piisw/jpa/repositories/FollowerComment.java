package com.piisw.jpa.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FollowerComment(
        String eventDescription,
        LocalDateTime eventTime,
        boolean analysisRequired,
        String commentContent,
        LocalDate subscriptionDate
) {}
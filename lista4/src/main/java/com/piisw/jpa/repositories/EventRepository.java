package com.piisw.jpa.repositories;

import com.piisw.jpa.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByTimeBetweenAndAnalysisRequired(
            LocalDateTime start,
            LocalDateTime end,
            boolean toBeAnalyzed,
            Pageable pageable
    );
}

package com.piisw.jpa.repositories;

import com.piisw.jpa.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByTimeBetweenAndAnalysisRequired(
            LocalDateTime start,
            LocalDateTime end,
            boolean toBeAnalyzed,
            Pageable pageable
    );

    @Modifying
    @Query("DELETE FROM Event e WHERE e.time < :threshold")
    void deleteByTimeBefore(@Param("threshold") LocalDateTime threshold);

    @Modifying
    @Query("""
    UPDATE Event e
    SET e.analysisRequired = true
    WHERE TYPE(e) = :clazz
    AND e.duration > :minDuration
""")
    int updateAnalysisRequiredForSubclass(
            @Param("clazz") Class<? extends Event> clazz,
            @Param("minDuration") int minDuration
    );
}

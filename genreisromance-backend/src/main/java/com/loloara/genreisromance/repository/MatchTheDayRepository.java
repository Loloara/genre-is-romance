package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.domain.MatchTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchTheDayRepository extends JpaRepository<MatchTheDay, Long> {
}

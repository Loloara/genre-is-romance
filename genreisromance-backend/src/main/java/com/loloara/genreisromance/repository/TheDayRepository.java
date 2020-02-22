package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.TheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheDayRepository extends JpaRepository<TheDay, Long> {
}

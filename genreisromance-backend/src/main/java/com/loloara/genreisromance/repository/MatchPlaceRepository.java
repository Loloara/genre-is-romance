package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.MatchPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchPlaceRepository extends JpaRepository<MatchPlace, Long> {
}

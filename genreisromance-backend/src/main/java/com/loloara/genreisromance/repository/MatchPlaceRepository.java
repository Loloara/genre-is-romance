package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.domain.MatchPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchPlaceRepository extends JpaRepository<MatchPlace, Long> {
}

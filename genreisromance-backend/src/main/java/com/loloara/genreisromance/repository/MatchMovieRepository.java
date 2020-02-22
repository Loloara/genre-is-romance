package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.MatchMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchMovieRepository extends JpaRepository<MatchMovie, Long> {
}

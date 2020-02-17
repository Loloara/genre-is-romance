package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("select m from match m where user_m = :userId or user_w = :userId order by m.last_updated_date asc limit 100;")
    Optional<Match> findByUserId(@Param("userId") Long userId);

    @Query("select case when count(m) > 0 then true else false end from match m where user_m = :userId or user_w = :userId;")
    boolean existsByUserId(@Param("userId") Long userId);
}

package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    @Query("select l from letter l where l.user = :userId;")
    Optional<Letter> findByUserId(@Param("userId") Long userId);

    @Query("select case when count(l) > 0 then true else false end from letter l where l.user = :userId;")
    boolean existsByUserId(@Param("userId") Long userId);
}

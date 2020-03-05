package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.domain.TheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheDayRepository extends JpaRepository<TheDay, Long> {
    @Query("select t from TheDay t left join fetch t.matchTheDays")
    List<TheDay> findFetchAll();

    @Query("select t from TheDay t join fetch t.matchTheDays where t.id = :id")
    Optional<TheDay> findByIdFetchAll(@Param("id") Long id);
}

package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select m from Movie m left join fetch m.matchMovies")
    List<Movie> findFetchAll();

    @Query("select m from Movie m join fetch m.matchMovies where m.id = :id")
    Optional<Movie> findByIdFetchAll(@Param("id") Long id);
}

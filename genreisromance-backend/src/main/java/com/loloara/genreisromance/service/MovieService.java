package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.domain.Movie;
import com.loloara.genreisromance.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService (MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void saveAll(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    public List<Movie> findFetchAll() {
        return movieRepository.findFetchAll();
    }

    public Movie findByIdFetchAll (Long movieId) {
        log.info("Find movie with all fetch by movieId : {}", movieId);
        return movieRepository.findByIdFetchAll(movieId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Movie findById(Long movieId) {
        log.info("Find movie by movieId : {}", movieId);
        return movieRepository.findById(movieId)
                    .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Movie movie) {
        log.info("Delete movie by object Movie : {}", movie.getId());
        movieRepository.delete(movie);
    }
}

package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.Movie;
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

    public Movie findById(Long movieId) {
        log.info("Find movie by movieId : {}", movieId);
        return movieRepository.findById(movieId)
                    .orElseThrow(IllegalArgumentException::new);
    }
}

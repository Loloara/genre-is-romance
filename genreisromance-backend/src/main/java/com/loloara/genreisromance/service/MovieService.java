package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.model.domain.Movie;
import com.loloara.genreisromance.model.dto.MovieDto;
import com.loloara.genreisromance.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService (MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieDto.MovieInfo registerMovie(MovieDto.Create movieDto) {
        if(movieRepository.existsById(movieDto.getMovieTitle())) {
            throw new ApiException("Movie Already exists.", HttpStatus.BAD_REQUEST);
        }

        Movie newMovie = Movie.builder()
                .movieTitle(movieDto.getMovieTitle())
                .build();

        return new MovieDto.MovieInfo(movieRepository.save(newMovie));
    }

    public MovieDto.MovieInfos findAll() {
        return new MovieDto.MovieInfos(movieRepository.findAll());
    }

    public MovieDto.MovieInfo updateMovie(MovieDto.Update movieDto) {
        if(movieDto.getMovieTitle() == null) {
            throw new IllegalArgumentException();
        }
        Movie movie = movieRepository.findById(movieDto.getMovieTitle())
                .orElseThrow(() -> new ApiException("Movie Not Found", HttpStatus.NOT_FOUND));
        movie.updateVal(movieDto);

        return new MovieDto.MovieInfo(movieRepository.save(movie));
    }
}

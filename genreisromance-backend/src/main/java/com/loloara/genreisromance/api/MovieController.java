package com.loloara.genreisromance.api;

import com.loloara.genreisromance.model.dto.MovieDto;
import com.loloara.genreisromance.service.MovieService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ApiOperation(value = "create movie", notes = "관리자가 영화 1개 추가")
    @PostMapping
    public ResponseEntity<MovieDto.MovieInfo> registerLetter(@RequestBody MovieDto.Create movieDto) {
        log.debug("REST request to save Movie List by admin : {}", movieDto);
        MovieDto.MovieInfo response = movieService.registerMovie(movieDto);
        return new ResponseEntity<MovieDto.MovieInfo>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "get movies", notes = "등록 된 영화 전부 얻기")
    @GetMapping
    public ResponseEntity<MovieDto.MovieInfos> getMoviesAll() {
        log.debug("REST request to get Movies all");
        MovieDto.MovieInfos response = movieService.findAll();
        return new ResponseEntity<MovieDto.MovieInfos>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "update movie", notes = "관리자가 영화 정보 업데이트")
    @PutMapping
    public ResponseEntity<MovieDto.MovieInfo> updateMovie(@RequestBody MovieDto.Update movieDto) {
        log.debug("REST request to update Movie Information : {}", movieDto.getMovieTitle());
        MovieDto.MovieInfo response = movieService.updateMovie(movieDto);
        return new ResponseEntity<MovieDto.MovieInfo>(response, HttpStatus.OK);
    }
}
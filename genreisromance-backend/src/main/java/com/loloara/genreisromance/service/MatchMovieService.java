package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.MatchMovie;
import com.loloara.genreisromance.repository.MatchMovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchMovieService {

    private final MatchMovieRepository matchMovieRepository;

    public MatchMovieService(MatchMovieRepository matchMovieRepository) {
        this.matchMovieRepository = matchMovieRepository;
    }

    public MatchMovie save(MatchMovie matchMovie) {
        return matchMovieRepository.save(matchMovie);
    }

    public MatchMovie findById(Long matchMovieId) {
        log.info("Find match_movie by matchM=ovieId : {}", matchMovieId);
        return matchMovieRepository.findById(matchMovieId)
                .orElseThrow(IllegalArgumentException::new);
    }
}

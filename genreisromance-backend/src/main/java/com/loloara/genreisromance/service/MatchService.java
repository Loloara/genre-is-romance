package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.Match;
import com.loloara.genreisromance.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchService {
    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match save (Match match) {
        return matchRepository.save(match);
    }

    public Match findById (Long matchId) {
        log.info("Find match by matchId : {}", matchId);
        return matchRepository.findById(matchId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Match findByUserId (Long userId) {
        log.info("Find match by userId : {}", userId);
        return matchRepository.findByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsByUserId (Long userId) {
        return matchRepository.existsByUserId(userId);
    }
}

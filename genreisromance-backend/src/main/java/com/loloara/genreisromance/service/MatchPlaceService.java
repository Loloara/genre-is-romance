package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.MatchPlace;
import com.loloara.genreisromance.repository.MatchPlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchPlaceService {

    private final MatchPlaceRepository matchPlaceRepository;

    public MatchPlaceService(MatchPlaceRepository matchPlaceRepository) {
        this.matchPlaceRepository = matchPlaceRepository;
    }

    public MatchPlace save(MatchPlace matchPlace) {
        return matchPlaceRepository.save(matchPlace);
    }

    public MatchPlace findById(Long matchPlaceId) {
        log.info("Find match_Place by matchM=ovieId : {}", matchPlaceId);
        return matchPlaceRepository.findById(matchPlaceId)
                .orElseThrow(IllegalArgumentException::new);
    }
}

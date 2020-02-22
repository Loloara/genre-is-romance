package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.MatchInfo;
import com.loloara.genreisromance.repository.MatchInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchInfoService {

    private final MatchInfoRepository matchInfoRepository;

    public MatchInfoService(MatchInfoRepository matchInfoRepository) {
        this.matchInfoRepository = matchInfoRepository;
    }

    public MatchInfo save (MatchInfo matchInfo) {
        return matchInfoRepository.save(matchInfo);
    }

    public MatchInfo findById (Long matcherId) {
        log.info("Find matcher by matcherId : {}", matcherId);
        return matchInfoRepository.findById(matcherId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public MatchInfo findByUserId (Long userId) {
        log.info("Find match by userId : {}", userId);
        return matchInfoRepository.findByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsByUserId (Long userId) {
        return matchInfoRepository.existsByUserId(userId);
    }
}

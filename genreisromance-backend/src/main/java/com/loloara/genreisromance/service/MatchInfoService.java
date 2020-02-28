package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.MatchInfo;
import com.loloara.genreisromance.repository.MatchInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<MatchInfo> findAll() {
        return matchInfoRepository.findAll();
    }

    public MatchInfo findById (Long matchInfoId) {
        log.info("Find matchInfo by matchInfoId : {}", matchInfoId);
        return matchInfoRepository.findById(matchInfoId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public MatchInfo findByIdFecthAll (Long matchInfoId) {
        log.info("Find matchInfo with all fetch by matchInfoId : {}", matchInfoId);
        return matchInfoRepository.findByIdFetchAll(matchInfoId)
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

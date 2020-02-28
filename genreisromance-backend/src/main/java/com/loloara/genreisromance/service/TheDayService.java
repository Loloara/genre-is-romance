package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.TheDay;
import com.loloara.genreisromance.repository.TheDayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TheDayService {

    private final TheDayRepository theDayRepository;

    public TheDayService(TheDayRepository theDayRepository) {
        this.theDayRepository = theDayRepository;
    }

    public TheDay save(TheDay theDay) {
        return theDayRepository.save(theDay);
    }

    public void saveAll(List<TheDay> theDays) {
        theDayRepository.saveAll(theDays);
    }

    public TheDay findByIdFetchAll (Long theDayId) {
        log.info("Find theDay with all fetch by theDayId : {}", theDayId);
        return theDayRepository.findByIdFetchAll(theDayId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public TheDay findById(Long theDayId) {
        log.info("Find theDay by theDayId : {}", theDayId);
        return theDayRepository.findById(theDayId)
                .orElseThrow(IllegalArgumentException::new);
    }
}

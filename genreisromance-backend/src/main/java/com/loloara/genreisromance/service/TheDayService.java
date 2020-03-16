package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.model.domain.TheDay;
import com.loloara.genreisromance.model.dto.TheDayDto;
import com.loloara.genreisromance.repository.TheDayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@Slf4j
public class TheDayService {

    private final TheDayRepository theDayRepository;

    public TheDayService(TheDayRepository theDayRepository) {
        this.theDayRepository = theDayRepository;
    }

    public TheDayDto.TheDayInfo registerTheDay(TheDayDto.Create theDayDto) {

        TheDay newTheDay = TheDay.builder()
                .dayDate(theDayDto.getDayDate())
                .dayTime(theDayDto.getDayTime())
                .build();

        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "createdDate", "lastModifiedDate", "matchTheDays")
                .withMatcher("dayTime", ignoreCase())
                .withMatcher("dayDate", ignoreCase());

        Example<TheDay> example = Example.of(newTheDay, modelMatcher);

        newTheDay = theDayRepository.findOne(example).orElse(newTheDay);

        return new TheDayDto.TheDayInfo(theDayRepository.save(newTheDay));
    }

    public TheDayDto.TheDayInfos findByDayDate(LocalDate dayDate) {
        return new TheDayDto.TheDayInfos(theDayRepository.findByDayDate(dayDate));
    }

    public TheDayDto.TheDayInfo updateTheDay(TheDayDto.Update theDayDto, Long theDayId) {
        TheDay theDay = theDayRepository.findById(theDayId)
                .orElseThrow(() -> new ApiException("TheDay Not Found", HttpStatus.NOT_FOUND));
        theDay.updateVal(theDayDto);

        return new TheDayDto.TheDayInfo(theDayRepository.save(theDay));
    }
}

package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.model.domain.*;
import com.loloara.genreisromance.model.dto.MatchInfoDto;
import com.loloara.genreisromance.repository.*;
import com.loloara.genreisromance.security.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MatchInfoService {

    private final MatchInfoRepository matchInfoRepository;
    private final MatchMovieRepository matchMovieRepository;
    private final MatchPlaceRepository matchPlaceRepository;
    private final MatchTheDayRepository matchTheDayRepository;

    public MatchInfoService(MatchInfoRepository matchInfoRepository, MatchMovieRepository matchMovieRepository,
                            MatchPlaceRepository matchPlaceRepository, MatchTheDayRepository matchTheDayRepository) {
        this.matchInfoRepository = matchInfoRepository;
        this.matchMovieRepository = matchMovieRepository;
        this.matchPlaceRepository = matchPlaceRepository;
        this.matchTheDayRepository = matchTheDayRepository;
    }

    public MatchInfoDto.MatchInfoResponse registerMatchInfo(MatchInfoDto.Create matchInfoDto) {
        Optional<MatchInfo> matchInfo = matchInfoRepository.findByUsersId(matchInfoDto.getUserMaleId(), matchInfoDto.getUserFemaleId());
        if(matchInfo.isPresent()) {
            return new MatchInfoDto.MatchInfoResponse(matchInfo.get());
        } else {
            MatchInfo newMatchInfo = MatchInfo.builder()
                    .userMaleId(new User(matchInfoDto.getUserMaleId()))
                    .userFemaleId(new User(matchInfoDto.getUserFemaleId()))
                    .build();
            return new MatchInfoDto.MatchInfoResponse(matchInfoRepository.save(newMatchInfo));
        }
    }

    @Transactional
    public MatchInfoDto.MatchMovies addMatchMovies(MatchInfoDto.AddMatchMovies matchInfoDto, CustomUserDetails currentUser) {
        MatchInfo matchInfo = matchInfoRepository.findByUserIdOnProcessFetchMovies(currentUser.getId())
                .orElseThrow(() -> new ApiException("Not Found MatchInfo Activated", HttpStatus.NOT_FOUND));

        if(!matchInfo.getMovies().isEmpty()) {
            matchMovieRepository.deleteAll(matchInfo.getMovies());
        }

        List<MatchMovie> matchMovies = new ArrayList<>();
        for(String s : matchInfoDto.getMovieIds()) {
            matchMovies.add(MatchMovie.builder()
                    .matchInfo(matchInfo)
                    .movie(Movie.builder().movieTitle(s).build())
                    .build());
        }

        if(matchMovies.isEmpty()) {
            return new MatchInfoDto.MatchMovies(matchMovies);
        }

        return new MatchInfoDto.MatchMovies(matchMovieRepository.saveAll(matchMovies));
    }

    @Transactional
    public MatchInfoDto.MatchPlaces addMatchPlaces(MatchInfoDto.AddMatchPlaces matchInfoDto, CustomUserDetails currentUser) {
        MatchInfo matchInfo = matchInfoRepository.findByUserIdOnProcessFetchPlaces(currentUser.getId())
                .orElseThrow(() -> new ApiException("Not Found MatchInfo Activated", HttpStatus.NOT_FOUND));

        if(!matchInfo.getPlaces().isEmpty()) {
            matchPlaceRepository.deleteAll(matchInfo.getPlaces());
        }

        List<MatchPlace> matchPlaces = new ArrayList<>();
        for(String s : matchInfoDto.getPlaceIds()) {
            matchPlaces.add(MatchPlace.builder()
                    .matchInfo(matchInfo)
                    .place(Place.builder().placeName(s).build())
                    .build());
        }

        if(matchPlaces.isEmpty()) {
            return new MatchInfoDto.MatchPlaces(matchPlaces);
        }

        return new MatchInfoDto.MatchPlaces(matchPlaceRepository.saveAll(matchPlaces));
    }

    @Transactional
    public MatchInfoDto.MatchTheDays addMatchTheDays(MatchInfoDto.AddMatchTheDays matchInfoDto, CustomUserDetails currentUser) {
        MatchInfo matchInfo = matchInfoRepository.findByUserIdOnProcessFetchTheDays(currentUser.getId())
                .orElseThrow(() -> new ApiException("Not Found MatchInfo Activated", HttpStatus.NOT_FOUND));

        if(!matchInfo.getThe_days().isEmpty()) {
            matchTheDayRepository.deleteAll(matchInfo.getThe_days());
        }

        List<MatchTheDay> matchTheDays = new ArrayList<>();
        for(Long l : matchInfoDto.getThedayIds()) {
            matchTheDays.add(MatchTheDay.builder()
                    .matchInfo(matchInfo)
                    .theDay(new TheDay(l))
                    .build());
        }

        if(matchTheDays.isEmpty()) {
            return new MatchInfoDto.MatchTheDays(matchTheDays);
        }

        return new MatchInfoDto.MatchTheDays(matchTheDayRepository.saveAll(matchTheDays));
    }

    public MatchInfoDto.MatchInfoResponse updateMatchInfo(Long matchInfoId, MatchInfoDto.UpdateMatchInfo matchInfoDto) {
        MatchInfo matchInfo = matchInfoRepository.findByUserIdOnProcess(matchInfoId)
                .orElseThrow(() -> new ApiException("Not Found MatchInfo Activated", HttpStatus.NOT_FOUND));
        matchInfo.updateVal(matchInfoDto);
        return new MatchInfoDto.MatchInfoResponse(matchInfoRepository.save(matchInfo));
    }
}

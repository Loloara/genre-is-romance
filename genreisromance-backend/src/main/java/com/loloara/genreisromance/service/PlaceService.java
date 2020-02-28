package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.Place;
import com.loloara.genreisromance.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Place save(Place place) {
        return placeRepository.save(place);
    }

    public void saveAll(List<Place> places) {
        placeRepository.saveAll(places);
    }

    public Place findByIdFetchAll (Long placeId) {
        log.info("Find place with all fetch by placeId : {}", placeId);
        return placeRepository.findByIdFetchAll(placeId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Place findById(Long placeId) {
        log.info("Find place by placeId : {}", placeId);
        return placeRepository.findById(placeId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
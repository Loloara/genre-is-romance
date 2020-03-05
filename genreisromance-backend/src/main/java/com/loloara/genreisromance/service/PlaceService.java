package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.domain.Place;
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

    public List<Place> findFetchAll() {
        return placeRepository.findFetchAll();
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

    public void delete(Place place) {
        log.info("Delete place by object Place : {}", place.getId());
        placeRepository.delete(place);
    }
}
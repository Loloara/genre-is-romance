package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.model.domain.Place;
import com.loloara.genreisromance.model.dto.PlaceDto;
import com.loloara.genreisromance.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public PlaceDto.PlaceInfo registerPlace(PlaceDto.Create placeDto) {
        if(placeRepository.existsById(placeDto.getPlaceName())) {
            throw new ApiException("Place Already exists.", HttpStatus.BAD_REQUEST);
        }

        Place newPlace = Place.builder()
                .placeName(placeDto.getPlaceName())
                .build();

        return new PlaceDto.PlaceInfo(placeRepository.save(newPlace));
    }

    public PlaceDto.PlaceInfos findAll() {
        return new PlaceDto.PlaceInfos(placeRepository.findAll());
    }

    public PlaceDto.PlaceInfo updatePlace(PlaceDto.Update placeDto) {
        if(placeDto.getPlaceName() == null) {
            throw new IllegalArgumentException();
        }
        Place place = placeRepository.findById(placeDto.getPlaceName())
                .orElseThrow(() -> new ApiException("Place Not Found", HttpStatus.NOT_FOUND));
        place.updateVal(placeDto);

        return new PlaceDto.PlaceInfo(placeRepository.save(place));
    }
}
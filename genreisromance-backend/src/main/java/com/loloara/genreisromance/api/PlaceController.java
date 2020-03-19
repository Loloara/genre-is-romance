package com.loloara.genreisromance.api;

import com.loloara.genreisromance.model.dto.PlaceDto;
import com.loloara.genreisromance.service.PlaceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/place")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @ApiOperation(value = "create place", notes = "관리자가 장소 1개 추가")
    @PostMapping
    public ResponseEntity<PlaceDto.PlaceInfo> registerPlace(@RequestBody PlaceDto.Create placeDto) {
        log.debug("REST request to save Movie List by admin : {}", placeDto);
        PlaceDto.PlaceInfo response = placeService.registerPlace(placeDto);
        return new ResponseEntity<PlaceDto.PlaceInfo>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "get places", notes = "등록 된 장소 전부 얻기")
    @GetMapping
    public ResponseEntity<PlaceDto.PlaceInfos> getPlacesAll() {
        log.debug("REST request to get Places all");
        PlaceDto.PlaceInfos response = placeService.findAll();
        return new ResponseEntity<PlaceDto.PlaceInfos>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "update place", notes = "관리자가 장소 정보 업데이트")
    @PutMapping
    public ResponseEntity<PlaceDto.PlaceInfo> updatePlace(@RequestBody PlaceDto.Update placeDto) {
        log.debug("REST request to update Place Information : {}", placeDto.getPlaceName());
        PlaceDto.PlaceInfo response = placeService.updatePlace(placeDto);
        return new ResponseEntity<PlaceDto.PlaceInfo>(response, HttpStatus.OK);
    }
}

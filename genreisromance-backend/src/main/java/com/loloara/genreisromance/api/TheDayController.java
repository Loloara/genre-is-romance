package com.loloara.genreisromance.api;

import com.loloara.genreisromance.model.dto.PlaceDto;
import com.loloara.genreisromance.model.dto.TheDayDto;
import com.loloara.genreisromance.service.TheDayService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/theday")
public class TheDayController {

    TheDayService theDayService;

    public TheDayController(TheDayService theDayService) {
        this.theDayService = theDayService;
    }

    @ApiOperation(value = "create the day", notes = "해당 날짜의 id값 얻기")
    @PostMapping
    public ResponseEntity<TheDayDto.TheDayInfo> registerTheDay(@RequestBody TheDayDto.Create theDayDto) {
        log.debug("REST request to save TheDay by admin : {}", theDayDto);
        TheDayDto.TheDayInfo response = theDayService.registerTheDay(theDayDto);
        return new ResponseEntity<TheDayDto.TheDayInfo>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "get the day by date", notes = "관리자가 해당 날짜에 등록 된 만남 전부 얻기")
    @GetMapping("/date/{dayDate}")
    public ResponseEntity<TheDayDto.TheDayInfos> getTheDayByDate(@PathVariable LocalDate dayDate) {
        log.debug("REST request to get TheDays by date : {}", dayDate);
        TheDayDto.TheDayInfos response = theDayService.findByDayDate(dayDate);
        return new ResponseEntity<TheDayDto.TheDayInfos>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "update the day", notes = "관리자가 날짜 정보 업데이트")
    @PutMapping("/{theDayId}")
    public ResponseEntity<TheDayDto.TheDayInfo> updateTheDay(@PathVariable Long theDayId, @RequestBody TheDayDto.Update theDayDto) {
        log.debug("REST request to update TheDay Information : {}", theDayId);
        TheDayDto.TheDayInfo response = theDayService.updateTheDay(theDayDto, theDayId);
        return new ResponseEntity<TheDayDto.TheDayInfo>(response, HttpStatus.OK);
    }
}

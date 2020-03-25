package com.loloara.genreisromance.api;

import com.loloara.genreisromance.common.annotation.CurrentUser;
import com.loloara.genreisromance.model.dto.MatchInfoDto;
import com.loloara.genreisromance.security.service.CustomUserDetails;
import com.loloara.genreisromance.service.MatchInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchInfoService matchInfoService;

    public MatchController(MatchInfoService matchInfoService) {
        this.matchInfoService = matchInfoService;
    }

    @ApiOperation(value = "create MatchInfo by Admin", notes = "관리자가 MatchInfo 만듬, 이미 존재시 존재하는 인스턴스 리턴")
    @PostMapping
    public ResponseEntity<MatchInfoDto.MatchInfoResponse> registerMatchInfo(@RequestBody MatchInfoDto.Create matchInfoDto) {
        log.debug("REST request to save MatchInfo : {}", matchInfoDto);
        MatchInfoDto.MatchInfoResponse response = matchInfoService.registerMatchInfo(matchInfoDto);
        return new ResponseEntity<MatchInfoDto.MatchInfoResponse>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "add MatchMovie", notes = "MatchInfo에 영화 추가")
    @PostMapping("/movie")
    public ResponseEntity<MatchInfoDto.MatchMovies> addMatchMovies(@RequestBody MatchInfoDto.AddMatchMovies matchInfoDto,
                                                                        @CurrentUser CustomUserDetails customUserDetails) {
        log.debug("REST request to add MatchMovies to " + customUserDetails.getId() + " : {}", matchInfoDto);
        MatchInfoDto.MatchMovies response = matchInfoService.addMatchMovies(matchInfoDto, customUserDetails);
        return new ResponseEntity<MatchInfoDto.MatchMovies>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "add MatchPlace", notes = "MatchInfo에 장소 추가")
    @PostMapping("/place")
    public ResponseEntity<MatchInfoDto.MatchPlaces> addMatchPlace(@RequestBody MatchInfoDto.AddMatchPlaces matchInfoDto,
                                                                        @CurrentUser CustomUserDetails customUserDetails) {
        log.debug("REST request to add MatchPlace to " + customUserDetails.getId() + " : {}", matchInfoDto);
        MatchInfoDto.MatchPlaces response = matchInfoService.addMatchPlaces(matchInfoDto, customUserDetails);
        return new ResponseEntity<MatchInfoDto.MatchPlaces>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "add MatchTheDay", notes = "MatchInfo에 날짜 추가")
    @PostMapping("/theday")
    public ResponseEntity<MatchInfoDto.MatchTheDays> addMatchTheDay(@RequestBody MatchInfoDto.AddMatchTheDays matchInfoDto,
                                                                         @CurrentUser CustomUserDetails customUserDetails) {
        log.debug("REST request to add MatchTheDay to " + customUserDetails.getId() + " : {}", matchInfoDto);
        MatchInfoDto.MatchTheDays response = matchInfoService.addMatchTheDays(matchInfoDto, customUserDetails);
        return new ResponseEntity<MatchInfoDto.MatchTheDays>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update MatchInfo", notes = "MatchInfo의 프로세스, toManager 메시지 변경")
    @PutMapping("process/{matchInfoId}")
    public ResponseEntity<MatchInfoDto.MatchInfoResponse> updateMatchInfo(@PathVariable Long matchInfoId,
                                                                          @RequestBody MatchInfoDto.UpdateMatchInfo matchInfoDto) {
        log.debug("REST request to update MatchInfo" + matchInfoId + " : {}", matchInfoDto);
        MatchInfoDto.MatchInfoResponse response = matchInfoService.updateMatchInfo(matchInfoId, matchInfoDto);
        return new ResponseEntity<MatchInfoDto.MatchInfoResponse>(response, HttpStatus.OK);
    }
}

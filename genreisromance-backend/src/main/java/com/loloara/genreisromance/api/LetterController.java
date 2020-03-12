package com.loloara.genreisromance.api;

import com.loloara.genreisromance.common.annotation.CurrentUser;
import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.Letter;
import com.loloara.genreisromance.model.dto.LetterDto;
import com.loloara.genreisromance.repository.LetterRepository;
import com.loloara.genreisromance.security.service.CustomUserDetails;
import com.loloara.genreisromance.service.LetterService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/letter")
public class LetterController {

    private final LetterService letterService;
    private final LetterRepository letterRepository;

    public LetterController(LetterService letterService, LetterRepository letterRepository) {
        this.letterService = letterService;
        this.letterRepository= letterRepository;
    }

    @ApiOperation(value = "create letter", notes = "create letter with current user")
    @PostMapping
    public ResponseEntity<LetterDto.LetterInfo> registerLetter(@RequestBody LetterDto.Create letterDto,
                                                           @CurrentUser CustomUserDetails customUserDetails) {
        log.debug("REST request to save Letter : {}", letterDto);
        LetterDto.LetterInfo response = letterService.registerLetter(letterDto, customUserDetails);
        return new ResponseEntity<LetterDto.LetterInfo>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "get letters by process", notes = "관리자 용 API (0~5) 프로세스에 해당되는 letters")
    @GetMapping("/process/{process}")
    public ResponseEntity<LetterDto.LetterInfos> getLettersByProcess(@PathVariable int process) {
        ProcessType processType = ProcessType.fromInteger(process);
        log.debug("REST request to get Letters by process : {}", processType);
        LetterDto.LetterInfos response = letterService.findByProcess(processType);
        return new ResponseEntity<LetterDto.LetterInfos>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get letter of current user", notes = "현재 유저의 letter 얻기")
    @GetMapping("/{userId}")
    public ResponseEntity<LetterDto.LetterInfo> getLetter(@PathVariable Long userId) {
        log.debug("REST request to get Letter of current user : {}", userId);
        Letter letter = letterRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("Letter does not exist", HttpStatus.NOT_FOUND));
        LetterDto.LetterInfo response = new LetterDto.LetterInfo(letter);
        return new ResponseEntity<LetterDto.LetterInfo>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "update letter", notes = "letter 수정")
    @PutMapping
    public ResponseEntity<LetterDto.LetterInfo> getLettersByProcess(@RequestBody LetterDto.Update letterDto,
                                                                    @CurrentUser CustomUserDetails customUserDetails) {
        log.debug("REST request to update Letter of current user : {}", customUserDetails.getUsername());
        LetterDto.LetterInfo response = letterService.updateLetter(letterDto, customUserDetails);
        return new ResponseEntity<LetterDto.LetterInfo>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "update process of letter by userId", notes = "관리자가 letter process 수정")
    @PutMapping("/admin/{userId}")
    public ResponseEntity<LetterDto.LetterInfo> getLettersByProcess(@PathVariable Long userId,
                                                                    @RequestBody LetterDto.UpdateProcess letterDto) {
        log.debug("REST request to update Letter of current user : {}", userId);
        LetterDto.LetterInfo response = letterService.updateLetterProcess(letterDto, userId);
        return new ResponseEntity<LetterDto.LetterInfo>(response, HttpStatus.OK);
    }
}

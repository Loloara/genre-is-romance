package com.loloara.genreisromance.api;

import com.loloara.genreisromance.common.annotation.CurrentUser;
import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.dto.LetterDto;
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

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
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
}

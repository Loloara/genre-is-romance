package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.Letter;
import com.loloara.genreisromance.model.domain.User;
import com.loloara.genreisromance.model.dto.LetterDto;
import com.loloara.genreisromance.repository.LetterRepository;
import com.loloara.genreisromance.security.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LetterService {
    private final LetterRepository letterRepository;

    public LetterService (LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    public LetterDto.LetterInfo registerLetter(LetterDto.Create letterDto, CustomUserDetails customUserDetails) {
        Letter newLetter = Letter.builder()
                .q1(letterDto.getQ1())
                .q2(letterDto.getQ2())
                .q3(letterDto.getQ3())
                .imagePath(letterDto.getImagePath())
                .process(ProcessType.SEARCHING)
                .user_id(new User(customUserDetails.getId()))
                .build();

        return new LetterDto.LetterInfo(letterRepository.save(newLetter));
    }

    public LetterDto.LetterInfos findByProcess(ProcessType processType) {
        return new LetterDto.LetterInfos(letterRepository.findByProcess(processType));
    }
}

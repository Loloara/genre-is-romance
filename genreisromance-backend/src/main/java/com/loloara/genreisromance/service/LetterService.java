package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.Letter;
import com.loloara.genreisromance.repository.LetterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LetterService {
    private final LetterRepository letterRepository;

    public LetterService (LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    @Transactional
    public Letter save (Letter letter) {
        return letterRepository.save(letter);
    }

    public Letter findById (Long letterId) {
        log.info("Find letter by letterId : {}", letterId);
        return letterRepository.findById(letterId)
                    .orElseThrow(IllegalArgumentException::new);
    }

    public Letter findByUserId (Long userId) {
        log.info("Find letter by userId : {}", userId);
        return letterRepository.findByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Letter> findAll () {
        return letterRepository.findAll();
    }

    public boolean existsById (Long letterId) {
        return letterRepository.existsById(letterId);
    }

    public boolean existsByUserId (Long userId) {
        return letterRepository.existsByUserId(userId);
    }
}

package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.Letter;
import com.loloara.genreisromance.model.User;
import com.loloara.genreisromance.repository.LetterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LetterService {
    private final LetterRepository letterRepository;

    public LetterService (LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    public Letter save (Letter letter) {
        return letterRepository.save(letter);
    }

    public Letter findById (Long letterId) {
        log.info("Find letter by letterId : {}", letterId);
        return letterRepository.findById(letterId)
                    .orElseThrow(IllegalArgumentException::new);
    }

    public Letter findByUserId (User user) {
        log.info("Find letter by userId : {}", user.getId());
        return letterRepository.findByUser(user)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsById (Long letterId) {
        return letterRepository.existsById(letterId);
    }

    public boolean existsByUserId (User user) {
        return letterRepository.existsByUser(user);
    }
}
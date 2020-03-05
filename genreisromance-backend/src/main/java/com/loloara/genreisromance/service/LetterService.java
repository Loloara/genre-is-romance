package com.loloara.genreisromance.service;

import com.loloara.genreisromance.model.domain.Letter;
import com.loloara.genreisromance.repository.LetterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void saveAll (List<Letter> letters) {
        letterRepository.saveAll(letters);
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

    public void delete(Letter letter) {
        log.info("Delete letter by object Letter : {}", letter.getId());
        letterRepository.delete(letter);
    }
}

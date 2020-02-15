package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.Letter;
import com.loloara.genreisromance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    Optional<Letter> findByUser(User user);
    boolean existsByUser(User user);
}

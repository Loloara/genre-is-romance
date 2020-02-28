package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.User;
import com.loloara.genreisromance.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public User findByUserId(Long userId) {
        log.info("Find user by id : {}", userId);
        return userRepository.findById(userId)
                    .orElseThrow(IllegalArgumentException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone)
                    .orElseThrow(IllegalArgumentException::new);
    }

    public List<User> findAllByUserId(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public List<Long> findAllByProcess(ProcessType process) {
        log.info("Find userIds by process : {}", process);
        return userRepository.findAllByProcess(process);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean existsById (Long userId) {
        return userRepository.existsById(userId);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public void delete(User user) {
        log.info("Delete user by object User : {}", user.getId());
        userRepository.delete(user);
    }
}

package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.common.util.AuthProvider;
import com.loloara.genreisromance.common.util.AuthoritiesConstant;
import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.User;
import com.loloara.genreisromance.model.domain.UserAuthority;
import com.loloara.genreisromance.model.dto.UserDto;
import com.loloara.genreisromance.repository.AuthorityRepository;
import com.loloara.genreisromance.repository.UserAuthorityRepository;
import com.loloara.genreisromance.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, UserAuthorityRepository userAuthorityRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userAuthorityRepository = userAuthorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerAccount(UserDto.Create userDto) {
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new ApiException("Email Already exists.", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.save(User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .userName(userDto.getUserName())
                .height(userDto.getHeight())
                .phone(userDto.getPhone())
                .gender(Gender.fromValue(userDto.getGender()))
                .birthDate(userDto.getBirthDate())
                .provider(AuthProvider.LOCAL)
                .build());

        authorityRepository.findById(AuthoritiesConstant.USER).ifPresent(
                auth -> {
                    UserAuthority userAuthority = userAuthorityRepository.save(
                            UserAuthority.builder().user(user).authority(auth).build());
        });

        return user;
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

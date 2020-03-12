package com.loloara.genreisromance.service;

import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.common.util.AuthProvider;
import com.loloara.genreisromance.common.util.AuthoritiesConstant;
import com.loloara.genreisromance.common.util.Gender;
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
    public User registerAccount(UserDto.Create userDto, String role) {
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

        authorityRepository.findById(role).ifPresent(
                auth -> userAuthorityRepository.save(UserAuthority.builder().user(user).authority(auth).build())
        );

        return user;
    }
}

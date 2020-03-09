package com.loloara.genreisromance.api;

import com.loloara.genreisromance.common.annotation.CurrentUser;
import com.loloara.genreisromance.common.exception.ApiException;
import com.loloara.genreisromance.model.domain.User;
import com.loloara.genreisromance.model.dto.UserDto;
import com.loloara.genreisromance.repository.UserRepository;
import com.loloara.genreisromance.security.jwt.JwtAuthResponse;
import com.loloara.genreisromance.security.jwt.JwtUtil;
import com.loloara.genreisromance.security.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Slf4j
@RestController @CrossOrigin
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final String AUTHORIZATION_HEADER = "Authorization";

    AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto.LoginRequest request,
                                                       @RequestParam(value = "rememberMe", defaultValue = "false", required = false) boolean rememberMe,
                                                       HttpServletResponse response) throws AuthenticationException {
        log.debug("REST request to authenticate : {}", request.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = "Bearer " + jwtUtil.createToken(authentication, rememberMe);
            response.addHeader(AUTHORIZATION_HEADER, jwt);
            return ResponseEntity.ok(new JwtAuthResponse(jwt));
        } catch (AuthenticationException ae) {
            log.trace("Authentication exception trace: {}", ae.getMessage());
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                    ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/user")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser CustomUserDetails CustomUserDetails) {
        log.debug("REST request to get user : {}", CustomUserDetails.getEmail());
        return userRepository.findById(CustomUserDetails.getId())
                .orElseThrow(() -> new ApiException("User", HttpStatus.NOT_FOUND));
    }
}

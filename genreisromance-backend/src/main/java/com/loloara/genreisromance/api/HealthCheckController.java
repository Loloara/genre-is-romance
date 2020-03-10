package com.loloara.genreisromance.api;

import com.loloara.genreisromance.common.annotation.CurrentUser;
import com.loloara.genreisromance.security.service.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController @CrossOrigin
public class HealthCheckController {

    @ApiOperation(value = "Health Checker", notes = "Authorization Header 필요 없습니다.")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(value = "Health Checker With Token", notes = "Token 을 가지고 health check")
    @GetMapping("/api/health")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> healthCheckApi(@CurrentUser CustomUserDetails customUserDetails) {
        log.info("current user: " + customUserDetails.toString());
        return ResponseEntity.ok("OK");
    }
}

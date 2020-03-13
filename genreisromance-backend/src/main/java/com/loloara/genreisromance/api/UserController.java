package com.loloara.genreisromance.api;

import com.loloara.genreisromance.common.annotation.CurrentUser;
import com.loloara.genreisromance.common.util.AuthoritiesConstant;
import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.User;
import com.loloara.genreisromance.model.dto.UserDto;
import com.loloara.genreisromance.repository.UserRepository;
import com.loloara.genreisromance.security.service.CustomUserDetails;
import com.loloara.genreisromance.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, UserRepository userRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";

    @ApiOperation(value = "Email 회원가입 API", notes = "Authorization Header 필요 없습니다.")
    @PostMapping
    public ResponseEntity<?> registerAccount(@Valid @RequestBody UserDto.Create userDto,
                                             @RequestParam(value = "isAdmin", defaultValue = "false", required = false) boolean isAdmin) throws IllegalArgumentException {
        if(StringUtils.isEmpty(userDto.getPassword()) &&
                (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 20)) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        String role = isAdmin ? AuthoritiesConstant.ADMIN : AuthoritiesConstant.USER;
        User result = userService.registerAccount(userDto, role);

        return new ResponseEntity<User>(result, HttpStatus.CREATED);
    }

    @ApiOperation(value = "get User by Email API", notes = "Authorization Header 필요 없습니다.")
    @GetMapping("/{email}")
    public ResponseEntity<UserDto.UserInfo> getUser(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDto.UserInfo.class))
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "get users by process", notes = "관리자 용 API (0~5) 프로세스에 해당되는 유저")
    @GetMapping("/process/{process}")
    public ResponseEntity<UserDto.UserInfos> getUsersByProcess(@PathVariable int process) {
        ProcessType processType = ProcessType.fromInteger(process);
        log.debug("REST request to get Users by process : {}", processType);
        UserDto.UserInfos response = userService.findByProcess(processType);
        return new ResponseEntity<UserDto.UserInfos>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "update User of Current User", notes = "현재는 비밀번호만 수정가능")
    @PutMapping
    public ResponseEntity<UserDto.UserInfo> updateLetter(@RequestBody UserDto.Update userDto,
                                                             @CurrentUser CustomUserDetails customUserDetails) {
        log.debug("REST request to update User of current user : {}", customUserDetails.getUsername());
        UserDto.UserInfo response = userService.updateUser(userDto, customUserDetails);
        return new ResponseEntity<UserDto.UserInfo>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "update process of letter by userId", notes = "관리자가 user process 수정")
    @PutMapping("/admin/{userId}")
    public ResponseEntity<UserDto.UserInfo> updateUserProcess(@PathVariable Long userId,
                                                                    @RequestBody UserDto.UpdateProcess userDto) {
        log.debug("REST request to update User process by userId : {}", userId);
        UserDto.UserInfo response = userService.updateUserProcess(userDto, userId);
        return new ResponseEntity<UserDto.UserInfo>(response, HttpStatus.OK);
    }
}

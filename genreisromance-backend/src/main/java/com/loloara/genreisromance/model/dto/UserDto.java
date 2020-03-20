package com.loloara.genreisromance.model.dto;

import com.loloara.genreisromance.common.annotation.Phone;
import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.User;
import com.loloara.genreisromance.model.domain.UserAuthority;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

public class UserDto {

    @Getter @Builder
    public static class Create {

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영어와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;

        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String userName;

        @NotNull(message = "키는 필수 입력 값입니다.")
        private Integer height;

        @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
        @Phone(message = "핸드폰 형식에 맞지 않습니다.")
        private String phone;

        @NotBlank(message = "성별은 필수 입력 값입니다.")
        private String gender;

        @NotNull(message = "생일은 필수 입력 값입니다.")
        private LocalDate birthDate;
    }

    @Getter @Setter @NoArgsConstructor(access = PROTECTED)
    public static class UserInfo {
        private Long id;
        private String email;
        private Set<String> authorities;

        public UserInfo(User user) {
            id = user.getId();
            email = user.getEmail();
            setAuthorities(user.getAuthorities());
        }

        public void setAuthorities(Set<UserAuthority> authorities) {
            this.authorities = authorities.stream().map( userAuthority -> userAuthority.getAuthority().getName()).collect(Collectors.toSet());
        }
    }

    @Getter @Setter
    public static class UserInfos {
        private List<UserInfo> userInfos;

        public UserInfos(List<User> users) {
            userInfos = new ArrayList<>();
            for(User u : users) {
                userInfos.add(new UserInfo(u));
            }
        }
    }

    @Getter @Builder
    public static class LoginRequest {

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영어와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;
    }

    @Getter @Setter @Builder
    public static class Update {

        private String password;
    }

    @Getter @Builder
    public static class UpdateProcess {

        private int process;
    }
}

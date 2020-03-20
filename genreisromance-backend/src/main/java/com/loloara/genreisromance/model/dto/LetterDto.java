package com.loloara.genreisromance.model.dto;

import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.Letter;
import com.loloara.genreisromance.model.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

public class LetterDto {

    @Getter @Builder
    public static class Create {

        @NotBlank(message = "Q1은 필수 입력 값입니다.")
        private String q1;

        @NotBlank(message = "Q2는 필수 입력 값입니다.")
        private String q2;

        @NotBlank(message = "Q3은 필수 입력 값입니다.")
        private String q3;

        @NotBlank(message = "이미지는 필수 입력 값입니다.")
        private String imagePath;
    }

    @Getter @Builder
    public static class Update {

        private String q1;
        private String q2;
        private String q3;
        private String imagePath;
    }

    @Getter @Builder
    public static class UpdateProcess {

        private int process;
    }

    @Getter @Setter @NoArgsConstructor(access = PROTECTED)
    public static class LetterInfo {

        private Long id;
        private String q1;
        private String q2;
        private String q3;
        private String imagePath;
        private ProcessType process;
        private User user_id;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public LetterInfo(Letter letter) {
            id = letter.getId();
            q1 = letter.getQ1();
            q2 = letter.getQ2();
            q3 = letter.getQ3();
            imagePath = letter.getImagePath();
            process = letter.getProcess();
            user_id = letter.getUser_id();
            createdDate = letter.getCreatedDate();
            lastModifiedDate = letter.getLastModifiedDate();
        }
    }

    @Getter @Setter
    public static class LetterInfos {
        private List<LetterInfo> letterInfos;

        public LetterInfos(List<Letter> letters) {
            letterInfos = new ArrayList<>();
            for(Letter l : letters) {
                letterInfos.add(new LetterInfo(l));
            }
        }
    }
}

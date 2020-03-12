package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.BaseEntity;
import com.loloara.genreisromance.model.dto.LetterDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Letter extends BaseEntity {
    /*
        mapping with id of User
     */

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String q1;

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String q2;

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String q3;

    @Size(min = 15, max = 18)
    @Column(name = "image_path", length = 18, nullable = false)
    private String imagePath;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process = ProcessType.SEARCHING;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user_id;

    public void setProcess(ProcessType process) {
        this.process = process;
    }

    public boolean updateVal(LetterDto.Update letterDto) {
        String newImagePath = letterDto.getImagePath();
        String newQ1 = letterDto.getQ1();
        String newQ2 = letterDto.getQ2();
        String newQ3 = letterDto.getQ3();
        if(newImagePath == null && newQ1 == null && newQ2 == null && newQ3 == null) {
            return false;
        }
        if(newImagePath != null) {
            this.imagePath = newImagePath;
        }
        if(newQ1 != null) {
            this.q1 = newQ1;
        }
        if(newQ2 != null) {
            this.q2 = newQ2;
        }
        if(newQ3 != null) {
            this.q3 = newQ3;
        }
        return true;
    }
}

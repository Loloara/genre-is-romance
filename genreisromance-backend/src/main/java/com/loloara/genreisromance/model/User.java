package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.common.util.ProcessType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Email
    @Size(min = 5, max = 30)
    @Column(length = 30, unique = true, nullable = false)
    private String email;

    @Size(min = 2, max = 5)
    @Column(name = "user_name", length = 5, nullable = false)
    private String userName;

    @NotNull
    private Integer height;

    @NotNull
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Transient
    private Integer age;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process = ProcessType.SEARCHING;

    public Integer getAge() {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
    }
}
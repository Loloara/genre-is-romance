package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.common.util.ProcessType;
import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Size(min = 5, max = 30)
    @NotNull
    private String email;

    @NotNull
    private String userName;

    @Nullable
    private Integer height;

    @NotNull
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process;

    @Transient
    private Integer age;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createdDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastUpdatedDate = new Date();
}

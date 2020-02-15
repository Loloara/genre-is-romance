package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.common.util.ProcessType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Temporal(TemporalType.DATE)
    @Column(name="birth_date", nullable = false)
    private Date birthDate;

    @Transient
    private Integer age;

    @Enumerated(EnumType.STRING)
    private ProcessType process = ProcessType.찾는중;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_date")
    private Date lastUpdatedDate = new Date();
}
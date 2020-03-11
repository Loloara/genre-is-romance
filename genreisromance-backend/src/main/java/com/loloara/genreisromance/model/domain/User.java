package com.loloara.genreisromance.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loloara.genreisromance.common.annotation.Phone;
import com.loloara.genreisromance.common.util.AuthProvider;
import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Email
    @Size(min = 5, max = 30)
    @Column(length = 30, unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false)
    private String password;

    @Size(min = 2, max = 10)
    @Column(name = "user_name", length = 10, nullable = false)
    private String userName;

    @NotNull
    private Integer height;

    @Phone
    @Size(min = 11, max = 13)
    @Column(length = 13, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthProvider provider;

    @Transient
    private Integer age;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process = ProcessType.SEARCHING;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserAuthority> authorities = new HashSet<>();

    public User(Long id) {
        super();
        this.id = id;
        this.email = "temp@gmail.com";
        this.password = "temp1@3$";
        this.userName = "temp";
        this.birthDate = LocalDate.now();
        this.gender = Gender.MALE;
        this.height = 200;
        this.phone = "01012341234";
        this.provider = AuthProvider.LOCAL;
    }

    public Integer getAge() {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
    }

    @PreRemove
    private void preRemove() {
        for(UserAuthority userAuthority : authorities) {
            userAuthority.setNullUser();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final User other = (User) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
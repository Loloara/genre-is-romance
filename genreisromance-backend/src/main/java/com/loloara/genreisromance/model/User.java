package com.loloara.genreisromance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loloara.genreisromance.common.annotation.Phone;
import com.loloara.genreisromance.common.util.AuthProvider;
import com.loloara.genreisromance.common.util.Gender;
import com.loloara.genreisromance.common.util.ProcessType;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Email
    @Size(min = 5, max = 30)
    @Column(length = 30, unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password_hash", nullable = true)
    private String password;

    @Size(min = 2, max = 5)
    @Column(name = "user_name", length = 5, nullable = false)
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
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name")})
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    public Integer getAge() {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
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
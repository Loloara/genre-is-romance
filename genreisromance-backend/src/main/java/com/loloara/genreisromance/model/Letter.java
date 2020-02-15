package com.loloara.genreisromance.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Letter extends BaseModel {
    /*
        mapping with id of User
     */

    @Id
    @NotNull
    @Column(name = "letter_id")
    private Long id;

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String Q1;

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String Q2;

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String Q3;

    @Size(min = 15, max = 18)
    @Column(name = "image_path", length = 18, nullable = false)
    private String imagePath;

    private Boolean pass = false;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
}

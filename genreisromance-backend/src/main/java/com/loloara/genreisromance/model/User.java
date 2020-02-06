package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String uid;

    @NotNull
    private String userName;
}

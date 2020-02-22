package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;

import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Movie extends BaseEntity {

    @Column(name = "movie_title", nullable = false)
    private String movieTitle;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private Set<MatchMovie> matchers;
}

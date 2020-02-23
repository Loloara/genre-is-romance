package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchMovie extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_info_id")
    @NotNull
    private MatchInfo matchInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    @NotNull
    private Movie movie;
}

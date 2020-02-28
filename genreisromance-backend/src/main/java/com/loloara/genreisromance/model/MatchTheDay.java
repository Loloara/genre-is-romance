package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchTheDay extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "match_info_id")
    @NotNull
    private MatchInfo matchInfo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "the_day_id")
    @NotNull
    private TheDay theDay;
}

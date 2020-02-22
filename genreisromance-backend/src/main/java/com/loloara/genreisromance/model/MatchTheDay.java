package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchTheDay extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_info_id")
    private MatchInfo matchInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "the_day_id")
    private TheDay theDay;
}

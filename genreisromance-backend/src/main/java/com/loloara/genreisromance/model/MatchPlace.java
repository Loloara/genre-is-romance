package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchPlace extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_info_id")
    private MatchInfo matchInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id")
    private Place place;

}

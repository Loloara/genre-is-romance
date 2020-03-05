package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchPlace extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "match_info_id")
    private MatchInfo matchInfo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "place_id")
    private Place place;

    public void setNullMatchInfo() {
        matchInfo = null;
    }

    public void setNullPlace() {
        place = null;
    }

    @PreRemove
    private void preRemove() {
        if(matchInfo != null) {
            matchInfo.getPlaces().remove(this);
        }
        if(place != null) {
            place.getMatchPlaces().remove(this);
        }
    }
}

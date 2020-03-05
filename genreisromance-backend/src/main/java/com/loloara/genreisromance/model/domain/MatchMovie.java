package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchMovie extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinColumn(name = "match_info_id")
    private MatchInfo matchInfo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public void setNullMatchInfo() {
        matchInfo = null;
    }

    public void setNullMovie() {
        movie = null;
    }

    @PreRemove
    private void preRemove() {
        if(matchInfo != null) {
            matchInfo.getMovies().remove(this);
        }
        if(movie != null) {
            movie.getMatchMovies().remove(this);
        }
    }
}

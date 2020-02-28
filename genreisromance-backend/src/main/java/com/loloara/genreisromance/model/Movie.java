package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Movie extends BaseEntity {

    @Column(name = "movie_title", nullable = false)
    private String movieTitle;

    @Builder.Default
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    private Set<MatchMovie> matchMovies = new HashSet<>();

    @PreRemove
    public void preRemove() {
        for(MatchMovie mv : matchMovies) {
            mv.setNullMovie();
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Movie other = (Movie) o;
        return this.id.equals(other.id);
    }

    public int hashCode() {
        if(id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }
}

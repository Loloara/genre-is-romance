package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.ProcessType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;


    /* ToDo
        change ProcessType for admin ex) ManToWoman, WomanToMan
        change data type for movies maybe should make validation for url
    */

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchInfo extends BaseEntity {
    /*
        it has two user ids. one is man's, the other is woman's
     */

    @Builder.Default
    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY)
    private Set<MatchMovie> movies = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY)
    private Set<MatchTheDay> the_days = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY)
    private Set<MatchPlace> places = new HashSet<>();

    @Builder.Default
    @Size(max = 100)
    @Column(name = "to_manager_from_w", length = 100, nullable = false)
    private String toManagerFromW = "nothing to say";

    @Builder.Default
    @Size(max = 100)
    @Column(name = "to_manager_from_m", length = 100, nullable = false)
    private String toManagerFromM = "nothing to say";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process = ProcessType.SEARCHING;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
    })
    @JoinColumn(name = "user_male_id", referencedColumnName = "id")
    private User userMaleId;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
    })
    @JoinColumn(name = "user_female_id", referencedColumnName = "id")
    private User userFemaleId;

    public void addMovie(MatchMovie matchMovie) {
        movies.add(matchMovie);
    }

    public void addPlace(MatchPlace matchPlace) {
        places.add(matchPlace);
    }

    public void addTheDay(MatchTheDay matchTheDay) {
        the_days.add(matchTheDay);
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
        final MatchInfo other = (MatchInfo) o;
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
package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.ProcessType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

    /* ToDo
        change ProcessType for admin ex) ManToWoman, WomanToMan
        change data type for movies maybe should make validation for url
    */

@Entity
@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class MatchInfo extends BaseEntity {
    /*
        it has two user ids. one is man's, the other is woman's
     */

    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY)
    private Set<MatchMovie> movies;

    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY)
    private Set<MatchTheDay> the_days;

    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY)
    private Set<MatchPlace> places;

    @Builder.Default
    @Size(max = 100)
    @Column(name = "to_manager_from_w", length = 100, nullable = false)
    private String toManagerFromW = "";

    @Builder.Default
    @Size(max = 100)
    @Column(name = "to_manager_from_m", length = 100, nullable = false)
    private String toManagerFromM = "";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process = ProcessType.SEARCHING;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
    })
    @JoinColumn(name = "user_male_id", referencedColumnName = "id")
    private User userMaleId;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
    })
    @JoinColumn(name = "user_female_id", referencedColumnName = "id")
    private User userFemaleId;
}
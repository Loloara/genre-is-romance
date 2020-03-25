package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.BaseEntity;
import com.loloara.genreisromance.model.dto.MatchInfoDto;
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
    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    private Set<MatchMovie> movies = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    private Set<MatchTheDay> the_days = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "matchInfo", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    private Set<MatchPlace> places = new HashSet<>();

    @Builder.Default
    @Size(max = 100)
    @Column(name = "message_from_w", length = 100, nullable = false)
    private String messageFromW = "nothing to say";

    @Builder.Default
    @Size(max = 100)
    @Column(name = "message_from_m", length = 100, nullable = false)
    private String messageFromM = "nothing to say";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process = ProcessType.SEARCHING;

    @Builder.Default
    @NotNull
    private boolean onProcess = true;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE
    })
    @JoinColumn(name = "user_male_id", referencedColumnName = "id")
    private User userMaleId;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE
    })
    @JoinColumn(name = "user_female_id", referencedColumnName = "id")
    private User userFemaleId;

    public void closeMatch() {
        onProcess = false;
    }

    public boolean updateVal(MatchInfoDto.UpdateMatchInfo matchInfoDto) {
        int newProcess = matchInfoDto.getProcess();
        String newMessageFromM = matchInfoDto.getMessageFromM();
        String newMessageFromW = matchInfoDto.getMessageFromW();
        if(newProcess == -1 && newMessageFromM == null && newMessageFromW == null) {
            return false;
        }
        if(newProcess > 0 && newProcess < 5) {
            this.process = ProcessType.fromInteger(newProcess);
        }
        if(newMessageFromM != null) {
            this.messageFromM = newMessageFromM;
        }
        if(newMessageFromW != null) {
            this.messageFromW = newMessageFromW;
        }
        return true;
    }

    @PreRemove
    private void preRemove() {
        for(MatchMovie mv : movies) {
            mv.setNullMatchInfo();
        }

        for(MatchPlace mp : places) {
            mp.setNullMatchInfo();
        }

        for(MatchTheDay mt : the_days) {
            mt.setNullMatchInfo();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MatchInfo other = (MatchInfo) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
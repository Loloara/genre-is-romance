package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.DayTime;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class TheDay extends BaseEntity {

    @Column(name = "day_date", nullable = false)
    private LocalDate dayDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_time", nullable = false)
    private DayTime dayTime;

    @Builder.Default
    @OneToMany(mappedBy = "theDay", fetch = FetchType.LAZY)
    private Set<MatchTheDay> matchTheDays = new HashSet<>();

    public void addMatchTheDay(MatchTheDay matchTheDay) {
        matchTheDays.add(matchTheDay);
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
        final TheDay other = (TheDay) o;
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
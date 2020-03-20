package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.common.util.DayTime;
import com.loloara.genreisromance.model.BaseEntity;
import com.loloara.genreisromance.model.dto.TheDayDto;
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
    @OneToMany(mappedBy = "theDay", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    private Set<MatchTheDay> matchTheDays = new HashSet<>();

    public TheDay(Long id) {
        this.id = id;
        dayDate = LocalDate.now();
        dayTime = DayTime._7PM;
    }

    public boolean updateVal(TheDayDto.Update theDayDto) {
        LocalDate newDayDate = theDayDto.getDayDate();
        DayTime newDayTime = theDayDto.getDayTime();
        if(newDayDate == null && newDayTime == null) {
            return false;
        }
        if(newDayDate != null) {
            dayDate = newDayDate;
        }
        if(newDayTime != null) {
            dayTime = newDayTime;
        }
        return true;
    }

    @PreRemove
    public void preRemove() {
        for(MatchTheDay matchTheDay : matchTheDays) {
            matchTheDay.setNullTheDay();
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
        final TheDay other = (TheDay) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
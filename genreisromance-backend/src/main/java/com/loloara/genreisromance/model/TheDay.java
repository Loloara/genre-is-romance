package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.DayTime;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class TheDay extends BaseEntity {

    @Column(name = "day_date", nullable = false)
    private LocalDate dayDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_time", nullable = false)
    private DayTime dayTime;

    @OneToMany(mappedBy = "theDay", fetch = FetchType.LAZY)
    private Set<MatchTheDay> matchers;
}
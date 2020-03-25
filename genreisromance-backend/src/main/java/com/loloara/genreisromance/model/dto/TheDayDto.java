package com.loloara.genreisromance.model.dto;

import com.loloara.genreisromance.common.util.DayTime;
import com.loloara.genreisromance.model.domain.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

public class TheDayDto {

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
    public static class Create {

        @NotNull(message = "날짜는 필수 입력 값입니다.")
        private LocalDate dayDate;

        @NotNull(message = "시간은 필수 입력 값입니다.")
        private DayTime dayTime;
    }

    @Getter @Builder
    public static class Update {

        private LocalDate dayDate;
        private DayTime dayTime;
    }

    @Getter @Setter
    @NoArgsConstructor(access = PROTECTED)
    public static class TheDayInfo {

        private Long id;
        private LocalDate dayDate;
        private DayTime dayTime;
        private Set<MatchInfo> matchInfos;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public TheDayInfo(TheDay theDay) {
            id = theDay.getId();
            dayDate = theDay.getDayDate();
            dayTime = theDay.getDayTime();
            setMatchInfos(theDay.getMatchTheDays());
            createdDate = theDay.getCreatedDate();
            lastModifiedDate = theDay.getLastModifiedDate();
        }

        public void setMatchInfos(Set<MatchTheDay> matchInfos) {
            this.matchInfos = matchInfos.stream().map(MatchTheDay::getMatchInfo).collect(Collectors.toSet());
        }
    }

    @Getter @Setter
    public static class TheDayInfos {
        private List<TheDayInfo> theDayInfos;

        public TheDayInfos(List<TheDay> theDays) {
            theDayInfos = new ArrayList<>();
            for(TheDay t : theDays) {
                theDayInfos.add(new TheDayInfo(t));
            }
        }
    }
}

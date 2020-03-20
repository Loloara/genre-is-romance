package com.loloara.genreisromance.model.dto;

import com.loloara.genreisromance.common.util.DayTime;
import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

public class MatchInfoDto {

    @Getter @Builder
    public static class Create {

        @NotNull(message = "남자 유저는 필수 입력 값입니다.")
        private Long userMaleId;

        @NotNull(message = "여자 유저는 필수 입력 값입니다.")
        private Long userFemaleId;
    }

    @Getter @Builder
    public static class AddMatchMovies {

        private List<String> movieIds;
    }

    @Getter @Builder
    public static class AddMatchPlaces {

        private List<String> placeIds;
    }

    @Getter @Builder
    public static class AddMatchTheDays {

        private List<Long> thedayIds;
    }

    @Getter @Setter
    @NoArgsConstructor(access = PROTECTED)
    public static class MatchInfoResponse {

        private Long id;
        private ProcessType process;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private Set<Movie> movies = new HashSet<>();
        private Set<TheDay> theDays = new HashSet<>();
        private Set<Place> places = new HashSet<>();

        public MatchInfoResponse(MatchInfo matchInfo) {
            id = matchInfo.getId();
            process = matchInfo.getProcess();
            createdDate = matchInfo.getCreatedDate();
            lastModifiedDate = matchInfo.getLastModifiedDate();
            setMovies(matchInfo.getMovies());
            setTheDays(matchInfo.getThe_days());
            setPlaces(matchInfo.getPlaces());
        }

        public void setMovies(Set<MatchMovie> movies) {
            this.movies = movies.stream().map(MatchMovie::getMovie).collect(Collectors.toSet());
        }

        public void setTheDays(Set<MatchTheDay> theDays) {
            this.theDays = theDays.stream().map(MatchTheDay::getTheDay).collect(Collectors.toSet());
        }

        public void setPlaces(Set<MatchPlace> places) {
            this.places = places.stream().map(MatchPlace::getPlace).collect(Collectors.toSet());
        }
    }

    @Getter @Setter
    public static class MatchInfosResponse {
        private List<MatchInfoResponse> matchInfoResponseList;

        public MatchInfosResponse(List<MatchInfo> matchInfos) {
            matchInfoResponseList = new ArrayList<>();
            for(MatchInfo m : matchInfos) {
                matchInfoResponseList.add(new MatchInfoResponse(m));
            }
        }
    }

    @Getter
    public static class MatchMovieInfo {
        private String movieTitle;
        private Long matchInfoId;

        public MatchMovieInfo(MatchMovie matchMovie) {
            movieTitle = matchMovie.getMovie().getMovieTitle();
            matchInfoId = matchMovie.getMatchInfo().getId();
        }
    }

    @Getter
    public static class MatchMovies {
        private List<MatchMovieInfo> matchMovieInfoList;

        public MatchMovies(List<MatchMovie> matchMovies) {
            matchMovieInfoList = new ArrayList<>();
            for(MatchMovie m : matchMovies) {
                matchMovieInfoList.add(new MatchMovieInfo(m));
            }
        }
    }

    @Getter
    public static class MatchPlaceInfo {
        private String placeName;
        private Long matchInfoId;

        public MatchPlaceInfo(MatchPlace matchPlace) {
            placeName = matchPlace.getPlace().getPlaceName();
            matchInfoId = matchPlace.getMatchInfo().getId();
        }
    }

    @Getter
    public static class MatchPlaces {
        private List<MatchPlaceInfo> matchPlaceInfoList;

        public MatchPlaces(List<MatchPlace> matchPlaces) {
            matchPlaceInfoList = new ArrayList<>();
            for(MatchPlace m : matchPlaces) {
                matchPlaceInfoList.add(new MatchPlaceInfo(m));
            }
        }
    }

    @Getter
    public static class MatchTheDayInfo {
        private LocalDate dayDate;
        private DayTime dayTime;
        private Long matchInfoId;

        public MatchTheDayInfo(MatchTheDay matchTheDay) {
            dayDate = matchTheDay.getTheDay().getDayDate();
            dayTime = matchTheDay.getTheDay().getDayTime();
            matchInfoId = matchTheDay.getMatchInfo().getId();
        }
    }

    @Getter
    public static class MatchTheDays {
        private List<MatchTheDayInfo> matchTheDayInfoList;

        public MatchTheDays(List<MatchTheDay> matchTheDays) {
            matchTheDayInfoList = new ArrayList<>();
            for(MatchTheDay m : matchTheDays) {
                matchTheDayInfoList.add(new MatchTheDayInfo(m));
            }
        }
    }

    @Getter @Builder
    public static class UpdateMatchInfo {
        private int process;
    }
}

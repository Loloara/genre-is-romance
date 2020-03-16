package com.loloara.genreisromance.model.dto;

import com.loloara.genreisromance.model.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

public class PlaceDto {

    @Getter @Builder
    public static class Create {

        @NotNull(message = "장소 이름은 필수 입력 값입니다.")
        private String placeName;
    }

    @Getter @Builder
    public static class Update {

        private String placeName;
    }

    @Getter @Setter
    @NoArgsConstructor(access = PROTECTED)
    public static class PlaceInfo {

        private Long id;
        private String placeName;
        private Set<MatchInfo> matchInfos;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public PlaceInfo(Place place) {
            id = place.getId();
            placeName = place.getPlaceName();
            setMatchInfos(place.getMatchPlaces());
            createdDate = place.getCreatedDate();
            lastModifiedDate = place.getLastModifiedDate();
        }

        public void setMatchInfos(Set<MatchPlace> matchInfos) {
            this.matchInfos = matchInfos.stream().map(MatchPlace::getMatchInfo).collect(Collectors.toSet());
        }
    }

    @Getter @Setter
    public static class PlaceInfos {
        private List<PlaceDto.PlaceInfo> placeInfos;

        public PlaceInfos(List<Place> places) {
            placeInfos = new ArrayList<>();
            for(Place p : places) {
                placeInfos.add(new PlaceDto.PlaceInfo(p));
            }
        }
    }
}

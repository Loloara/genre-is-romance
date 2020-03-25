package com.loloara.genreisromance.model.dto;

import com.loloara.genreisromance.model.domain.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

public class PlaceDto {

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
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

        private String placeName;
        private Set<MatchInfo> matchInfos;

        public PlaceInfo(Place place) {
            placeName = place.getPlaceName();
            setMatchInfos(place.getMatchPlaces());
        }

        public void setMatchInfos(Set<MatchPlace> matchInfos) {
            this.matchInfos = matchInfos.stream().map(MatchPlace::getMatchInfo).collect(Collectors.toSet());
        }
    }

    @Getter @Setter
    public static class PlaceInfos {
        private List<PlaceInfo> placeInfos;

        public PlaceInfos(List<Place> places) {
            placeInfos = new ArrayList<>();
            for(Place p : places) {
                placeInfos.add(new PlaceInfo(p));
            }
        }
    }
}

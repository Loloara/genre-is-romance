package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.model.BaseModel;
import com.loloara.genreisromance.model.dto.PlaceDto;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Place extends BaseModel {

    @Id
    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Builder.Default
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    private Set<MatchPlace> matchPlaces = new HashSet<>();

    public boolean updateVal(PlaceDto.Update placeDto) {
        String newPlaceName = placeDto.getPlaceName();
        if(newPlaceName == null) {
            return false;
        } else {
            placeName = newPlaceName;
        }
        return true;
    }

    @PreRemove
    public void preRemove() {
        for(MatchPlace mp : matchPlaces) {
            mp.setNullPlace();
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
        final Place other = (Place) o;
        return placeName.equals(other.placeName);
    }

    @Override
    public int hashCode() {
        return placeName != null ? placeName.hashCode() : super.hashCode();
    }
}

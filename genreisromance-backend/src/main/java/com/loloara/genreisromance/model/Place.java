package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Place extends BaseEntity {

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Builder.Default
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    private Set<MatchPlace> matchPlaces = new HashSet<>();

    @PreRemove
    public void preRemove() {
        for(MatchPlace mp : matchPlaces) {
            mp.setNullPlace();
        }
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
        final Place other = (Place) o;
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

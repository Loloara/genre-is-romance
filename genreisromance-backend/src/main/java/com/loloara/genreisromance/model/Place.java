package com.loloara.genreisromance.model;

import lombok.*;

import javax.persistence.*;

import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Place extends BaseEntity {

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private Set<MatchPlace> matchers;
}

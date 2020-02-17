package com.loloara.genreisromance.model;

import com.loloara.genreisromance.common.util.ProcessType;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

    /* ToDo
        change ProcessType for admin ex) ManToWoman, WomanToMan
        change data type for movies maybe should make validation for url
    */

@Entity
@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Match extends BaseModel {
    /*
        it has two user ids. one is man's, the other is woman's
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @Column(name = "movie_set", nullable = false)
    private Set<String> movieSet;

    @Column(name = "date_set", nullable = false)
    private Set<LocalDate> dateSet;

    @Column(name = "place_set", nullable = false)
    private Set<String> placeSet;

    @Size(max = 100)
    @Column(name = "to_manager_from_w", length = 100, nullable = false)
    private String toManagerFromW = "";

    @Size(max = 100)
    @Column(name = "to_manager_from_m", length = 100, nullable = false)
    private String toManagerFromM = "";

    @NotNull
    private ProcessType process = ProcessType.찾는중;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "user_id")
    @Column(name = "user_m", nullable = false)
    private User user_m;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "user_id")
    @Column(name = "user_w", nullable = false)
    private User user_w;
}
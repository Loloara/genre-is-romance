package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Letter extends BaseEntity {
    /*
        mapping with id of User
     */

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String Q1;

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String Q2;

    @Size(min = 0, max = 100)
    @Column(length = 100, nullable = false)
    private String Q3;

    @Size(min = 15, max = 18)
    @Column(name = "image_path", length = 18, nullable = false)
    private String imagePath;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcessType process = ProcessType.SEARCHING;

    @OneToOne(fetch = FetchType.EAGER, optional = false,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            })
    @JoinColumn(name = "user_id")
    @NotNull
    private User user_id;
}

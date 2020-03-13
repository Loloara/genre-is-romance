package com.loloara.genreisromance.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loloara.genreisromance.model.BaseModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class Authority extends BaseModel {

    @Id
    @Size(min = 0, max = 20)
    @Column(length = 20, nullable = false)
    private String name;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    private Set<UserAuthority> users = new HashSet<>();

    @PreRemove
    public void preRemove() {
        for(UserAuthority userAuthority : users) {
            userAuthority.setNullAuthority();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        final Authority authority = (Authority) o;
        return name.equals(authority.getName());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

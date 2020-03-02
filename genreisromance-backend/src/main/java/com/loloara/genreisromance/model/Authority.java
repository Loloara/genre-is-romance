package com.loloara.genreisromance.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity @Getter @Builder
public class Authority extends BaseModel {

    @Id
    @Size(min = 0, max = 20)
    @Column(length = 20, nullable = false)
    private String name;

    Authority(String name) {
        this.name = name;
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

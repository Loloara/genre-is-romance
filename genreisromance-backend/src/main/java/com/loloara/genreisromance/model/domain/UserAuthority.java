package com.loloara.genreisromance.model.domain;

import com.loloara.genreisromance.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
public class UserAuthority extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "authority_name")
    private Authority authority;

    public void setNullUser() {
        user = null;
    }

    public void setNullAuthority() {
        authority = null;
    }

    @PreRemove
    private void preRemove() {
        if(authority != null) {
            authority.getUsers().remove(this);
        }
        if(user != null) {
            user.getAuthorities().remove(this);
        }
    }
}

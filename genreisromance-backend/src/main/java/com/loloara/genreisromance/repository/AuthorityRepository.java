package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    @Query("select a from Authority a join fetch a.users where a.name = :name")
    Optional<Authority> findByNameFetchAll(@Param("name") String name);
}

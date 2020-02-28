package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/* ToDo
    add order by and limit for findByUserId query
    check existsByUserId working
 */

@Repository
public interface MatchInfoRepository extends JpaRepository<MatchInfo, Long> {

    @Query("select m from MatchInfo m join fetch m.the_days join fetch m.movies join fetch m.places where m.id = :id")
    Optional<MatchInfo> findByIdFetchAll(@Param("id") Long id);

    @Query("select m from MatchInfo m where userMaleId = :userId or user_female_id = :userId")
    Optional<MatchInfo> findByUserId(@Param("userId") Long userId);

    @Query("select case when count(m) > 0 then true else false end from MatchInfo m where userMaleId = :userId or userFemaleId = :userId")
    boolean existsByUserId(@Param("userId") Long userId);
}

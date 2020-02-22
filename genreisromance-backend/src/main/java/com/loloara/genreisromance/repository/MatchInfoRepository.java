package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/* ToDo
    add order by and limit for findByUserId query
 */

@Repository
public interface MatchInfoRepository extends JpaRepository<MatchInfo, Long> {

    @Query("select m from MatchInfo m where user_male_id = :userId or user_female_id = :userId")
    Optional<MatchInfo> findByUserId(@Param("userId") Long userId);

    @Query("select case when count(m) > 0 then true else false end from MatchInfo m where user_male_id = :userId or user_female_id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);
}

package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.model.domain.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    List<MatchInfo> findByUserId(@Param("userId") Long userId);

    @Query("select m from MatchInfo m where (userMaleId = :userId or user_female_id = :userId) and onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcess(@Param("userId") Long userId);

    @Query("select m from MatchInfo m join fetch m.movies where (userMaleId = :userId or user_female_id = :userId) and onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcessFetchMovies(@Param("userId") Long userId);

    @Query("select m from MatchInfo m join fetch m.places where (userMaleId = :userId or user_female_id = :userId) and onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcessFetchPlaces(@Param("userId") Long userId);

    @Query("select m from MatchInfo m join fetch m.the_days where (userMaleId = :userId or user_female_id = :userId) and onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcessFetchTheDays(@Param("userId") Long userId);

    @Query("select m from MatchInfo m where userMaleId = :maleId and user_female_id = :femaleId")
    Optional<MatchInfo> findByUsersId(@Param("maleId") Long maleId, @Param("femaleId") Long femaleId);

    @Query("select case when count(m) > 0 then true else false end from MatchInfo m where userMaleId = :maleId and user_female_id = :femaleId")
    boolean existsByUsersId(@Param("maleId") Long maleId, @Param("femaleId") Long femaleId);
}

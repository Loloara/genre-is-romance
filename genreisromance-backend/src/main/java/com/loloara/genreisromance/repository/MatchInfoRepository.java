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

    @Query("select m from MatchInfo m left join fetch m.the_days left join fetch m.movies left join fetch m.places where m.id = :id")
    Optional<MatchInfo> findByIdFetchAll(@Param("id") Long id);

    @Query("select m from MatchInfo m where m.userMaleId.id = :userId or m.userFemaleId.id = :userId")
    List<MatchInfo> findByUserId(@Param("userId") Long userId);

    @Query("select m from MatchInfo m where (m.userMaleId.id = :userId or m.userFemaleId.id = :userId) and m.onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcess(@Param("userId") Long userId);

    @Query("select m from MatchInfo m left join fetch m.movies where (m.userMaleId.id = :userId or m.userFemaleId.id = :userId) and m.onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcessFetchMovies(@Param("userId") Long userId);

    @Query("select m from MatchInfo m left join fetch m.places where (m.userMaleId.id = :userId or m.userFemaleId.id = :userId) and m.onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcessFetchPlaces(@Param("userId") Long userId);

    @Query("select m from MatchInfo m left join fetch m.the_days where (m.userMaleId.id = :userId or m.userFemaleId.id = :userId) and m.onProcess = true")
    Optional<MatchInfo> findByUserIdOnProcessFetchTheDays(@Param("userId") Long userId);

    @Query("select m from MatchInfo m where m.userMaleId.id = :maleId and m.userFemaleId.id = :femaleId and m.onProcess = true")
    Optional<MatchInfo> findByUsersId(@Param("maleId") Long maleId, @Param("femaleId") Long femaleId);

    @Query("select case when count(m) > 0 then true else false end from MatchInfo m where m.userMaleId.id = :maleId and m.userFemaleId.id = :femaleId")
    boolean existsByUsersId(@Param("maleId") Long maleId, @Param("femaleId") Long femaleId);
}

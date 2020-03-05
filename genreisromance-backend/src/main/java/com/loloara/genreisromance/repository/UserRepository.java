package com.loloara.genreisromance.repository;

import com.loloara.genreisromance.common.util.ProcessType;
import com.loloara.genreisromance.model.domain.User;
import com.sun.tools.javac.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/* ToDo
    add order by and limit for findAllByProcess query
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);

    @Query("select u from User u join fetch u.authorities where u.email = :email")
    Optional<User> findByEmailFetchAll(@Param("email") String email);

    @Query("select id from User where process = :process")
    List<Long> findAllByProcess(@Param("process") ProcessType process);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Transactional
    @Modifying
    void deleteByEmail(String email);
}

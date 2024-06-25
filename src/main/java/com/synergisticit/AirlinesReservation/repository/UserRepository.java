package com.synergisticit.AirlinesReservation.repository;

import com.synergisticit.AirlinesReservation.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1", nativeQuery = true)
    Long getLastUserId();

    User findByEmail(String email);
    User findByUsername(String username);
}

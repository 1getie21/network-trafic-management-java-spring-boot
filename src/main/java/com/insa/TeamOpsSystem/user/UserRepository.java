package com.insa.TeamOpsSystem.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SystemUsers, Long> {
    Page<SystemUsers> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<SystemUsers> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}

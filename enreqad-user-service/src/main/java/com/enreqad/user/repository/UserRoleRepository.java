package com.enreqad.user.repository;

import com.enreqad.user.entity.profile.UserRole;
import com.enreqad.user.entity.profile.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(UserRoleEnum roleEnum);
}

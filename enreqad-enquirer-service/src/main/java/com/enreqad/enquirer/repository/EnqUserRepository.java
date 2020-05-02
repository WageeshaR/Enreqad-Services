package com.enreqad.enquirer.repository;

import com.enreqad.enquirer.entity.EnqUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnqUserRepository extends JpaRepository<EnqUser, Long> {}

package com.enreqad.advertiser.repository;

import com.enreqad.advertiser.entity.AdvUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvUserRepository extends JpaRepository<AdvUser, Long> {}

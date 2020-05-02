package com.enreqad.requirer.repository;

import com.enreqad.requirer.entity.ReqUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqUserRepository extends JpaRepository<ReqUser, Long> {}

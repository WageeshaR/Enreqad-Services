package com.enreqad.enquirer.repository;

import com.enreqad.enquirer.entity.feed.Pub;
import com.enreqad.enquirer.entity.feed.PubImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PubImageRepository extends JpaRepository<PubImage, Long> {
    Optional<PubImage> findByPub(Pub q);
}

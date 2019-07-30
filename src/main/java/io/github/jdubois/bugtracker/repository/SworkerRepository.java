package io.github.jdubois.bugtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.jdubois.bugtracker.domain.Sworker;

/**
 * Spring Data  repository for the Sworker entity.
 */
@Repository
public interface SworkerRepository extends JpaRepository<Sworker, Long> {

}

package io.github.jdubois.bugtracker.repository;

import io.github.jdubois.bugtracker.domain.Sworker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sworker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SworkerRepository extends JpaRepository<Sworker, Long> {

}

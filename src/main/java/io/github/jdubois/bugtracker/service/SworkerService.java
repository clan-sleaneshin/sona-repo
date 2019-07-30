package io.github.jdubois.bugtracker.service;

import io.github.jdubois.bugtracker.domain.Sworker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Sworker}.
 */
public interface SworkerService {

    /**
     * Save a sworker.
     *
     * @param sworker the entity to save.
     * @return the persisted entity.
     */
    Sworker save(Sworker sworker);

    /**
     * Get all the sworkers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Sworker> findAll(Pageable pageable);


    /**
     * Get the "id" sworker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sworker> findOne(Long id);

    /**
     * Delete the "id" sworker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

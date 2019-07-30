package io.github.jdubois.bugtracker.service.impl;

import io.github.jdubois.bugtracker.service.SworkerService;
import io.github.jdubois.bugtracker.domain.Sworker;
import io.github.jdubois.bugtracker.repository.SworkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Sworker}.
 */
@Service
@Transactional
public class SworkerServiceImpl implements SworkerService {

    private final Logger log = LoggerFactory.getLogger(SworkerServiceImpl.class);

    private final SworkerRepository sworkerRepository;

    public SworkerServiceImpl(SworkerRepository sworkerRepository) {
        this.sworkerRepository = sworkerRepository;
    }

    /**
     * Save a sworker.
     *
     * @param sworker the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Sworker save(Sworker sworker) {
        log.debug("Request to save Sworker : {}", sworker);
        return sworkerRepository.save(sworker);
    }

    /**
     * Get all the sworkers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Sworker> findAll(Pageable pageable) {
        log.debug("Request to get all Sworkers");
        return sworkerRepository.findAll(pageable);
    }


    /**
     * Get one sworker by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Sworker> findOne(Long id) {
        log.debug("Request to get Sworker : {}", id);
        return sworkerRepository.findById(id);
    }

    /**
     * Delete the sworker by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sworker : {}", id);
        sworkerRepository.deleteById(id);
    }
}

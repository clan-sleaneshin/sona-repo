package io.github.jdubois.bugtracker.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.jdubois.bugtracker.domain.Sworker;
import io.github.jdubois.bugtracker.repository.SworkerRepository;
import io.github.jdubois.bugtracker.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link io.github.jdubois.bugtracker.domain.Sworker}.
 */
@RestController
@RequestMapping("/api")
public class SworkerResource {

    private final Logger log = LoggerFactory.getLogger(SworkerResource.class);

    private static final String ENTITY_NAME = "sworker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SworkerRepository sworkerRepository;

    public SworkerResource(SworkerRepository sworkerRepository) {
        this.sworkerRepository = sworkerRepository;
    }

    /**
     * {@code POST  /Sworker} : Create a new Sxx Worker.
     *
     * @param sworker the Sxx Worker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new Sxx Worker, or with status {@code 400 (Bad Request)} if the
     *         Sxx Worker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sworkers")
    public ResponseEntity<Sworker> createSworker(@Valid @RequestBody Sworker sworker) throws URISyntaxException {
        log.debug("REST request to save Sxx Worker : {}", sworker);
        if (sworker.getId() != null) {
            throw new BadRequestAlertException("A new Sxx Worker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sworker result = sworkerRepository.save(sworker);
        return ResponseEntity
                .created(new URI("/api/sworkers/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /sworkers} : Updates an existing Sxx Worker.
     *
     * @param sworker the Sxx Worker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated Sxx Worker, or with status {@code 400 (Bad Request)} if
     *         the Sxx Worker is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the Sxx Worker couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sworkers")
    public ResponseEntity<Sworker> updateSworker(@Valid @RequestBody Sworker sworker) throws URISyntaxException {
        log.debug("REST request to update Sxx Worker : {}", sworker);
        if (sworker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sworker result = sworkerRepository.save(sworker);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sworker.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /sworkers} : get all the Sxx Worker.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of Sxx Workers in body.
     */
    @GetMapping("/sworkers")
    public ResponseEntity<List<Sworker>> getAllSworkers(Pageable pageable,
            @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Sxx Workers");
        Page<Sworker> page;
        page = sworkerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sworkers/:id} : get the "id" Sxx Worker.
     *
     * @param id the id of the Sxx Worker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the Sxx Worker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sworkers/{id}")
    public ResponseEntity<Sworker> getSworker(@PathVariable Long id) {
        log.debug("REST request to get Sxx Worker : {}", id);
        Optional<Sworker> sworker = sworkerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sworker);
    }

    /**
     * {@code DELETE  /sworkers/:id} : delete the "id" Sxx Worker.
     *
     * @param id the id of the Sxx Worker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sworkers/{id}")
    public ResponseEntity<Void> deleteSworker(@PathVariable Long id) {
        log.debug("REST request to delete Sxx Worker : {}", id);
        sworkerRepository.deleteById(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}

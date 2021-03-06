package com.ec.opensesame.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ec.opensesame.service.HistoryService;
import com.ec.opensesame.web.rest.errors.BadRequestAlertException;
import com.ec.opensesame.web.rest.util.HeaderUtil;
import com.ec.opensesame.service.dto.HistoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing History.
 */
@RestController
@RequestMapping("/api")
public class HistoryResource {

    private final Logger log = LoggerFactory.getLogger(HistoryResource.class);

    private static final String ENTITY_NAME = "history";

    private final HistoryService historyService;

    public HistoryResource(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * POST  /histories : Create a new history.
     *
     * @param historyDTO the historyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historyDTO, or with status 400 (Bad Request) if the history has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/histories")
    @Timed
    public ResponseEntity<HistoryDTO> createHistory(@RequestBody HistoryDTO historyDTO) throws URISyntaxException {
        log.debug("REST request to save History : {}", historyDTO);
        if (historyDTO.getId() != null) {
            throw new BadRequestAlertException("A new history cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoryDTO result = historyService.save(historyDTO);
        return ResponseEntity.created(new URI("/api/histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /histories : Updates an existing history.
     *
     * @param historyDTO the historyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historyDTO,
     * or with status 400 (Bad Request) if the historyDTO is not valid,
     * or with status 500 (Internal Server Error) if the historyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/histories")
    @Timed
    public ResponseEntity<HistoryDTO> updateHistory(@RequestBody HistoryDTO historyDTO) throws URISyntaxException {
        log.debug("REST request to update History : {}", historyDTO);
        if (historyDTO.getId() == null) {
            return createHistory(historyDTO);
        }
        HistoryDTO result = historyService.save(historyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /histories : get all the histories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of histories in body
     */
    @GetMapping("/histories")
    @Timed
    public List<HistoryDTO> getAllHistories() {
        log.debug("REST request to get all Histories");
        return historyService.findAll();
        }

    /**
     * GET  /histories/:id : get the "id" history.
     *
     * @param id the id of the historyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/histories/{id}")
    @Timed
    public ResponseEntity<HistoryDTO> getHistory(@PathVariable Long id) {
        log.debug("REST request to get History : {}", id);
        HistoryDTO historyDTO = historyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(historyDTO));
    }

    /**
     * DELETE  /histories/:id : delete the "id" history.
     *
     * @param id the id of the historyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        log.debug("REST request to delete History : {}", id);
        historyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package org.ptit.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.ptit.domain.PaymentTransaction;
import org.ptit.repository.PaymentTransactionRepository;
import org.ptit.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.ptit.domain.PaymentTransaction}.
 */
@RestController
@RequestMapping("/api/payment-transactions")
@Transactional
public class PaymentTransactionResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionResource.class);

    private static final String ENTITY_NAME = "paymentTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentTransactionRepository paymentTransactionRepository;

    public PaymentTransactionResource(PaymentTransactionRepository paymentTransactionRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
    }

    /**
     * {@code POST  /payment-transactions} : Create a new paymentTransaction.
     *
     * @param paymentTransaction the paymentTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentTransaction, or with status {@code 400 (Bad Request)} if the paymentTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentTransaction> createPaymentTransaction(@RequestBody PaymentTransaction paymentTransaction)
        throws URISyntaxException {
        log.debug("REST request to save PaymentTransaction : {}", paymentTransaction);
        if (paymentTransaction.getId() != null) {
            throw new BadRequestAlertException("A new paymentTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentTransaction result = paymentTransactionRepository.save(paymentTransaction);
        return ResponseEntity
            .created(new URI("/api/payment-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-transactions/:id} : Updates an existing paymentTransaction.
     *
     * @param id the id of the paymentTransaction to save.
     * @param paymentTransaction the paymentTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTransaction,
     * or with status {@code 400 (Bad Request)} if the paymentTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentTransaction> updatePaymentTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentTransaction paymentTransaction
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentTransaction : {}, {}", id, paymentTransaction);
        if (paymentTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentTransaction result = paymentTransactionRepository.save(paymentTransaction);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-transactions/:id} : Partial updates given fields of an existing paymentTransaction, field will ignore if it is null
     *
     * @param id the id of the paymentTransaction to save.
     * @param paymentTransaction the paymentTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTransaction,
     * or with status {@code 400 (Bad Request)} if the paymentTransaction is not valid,
     * or with status {@code 404 (Not Found)} if the paymentTransaction is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentTransaction> partialUpdatePaymentTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentTransaction paymentTransaction
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentTransaction partially : {}, {}", id, paymentTransaction);
        if (paymentTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentTransaction> result = paymentTransactionRepository
            .findById(paymentTransaction.getId())
            .map(existingPaymentTransaction -> {
                if (paymentTransaction.getBankId() != null) {
                    existingPaymentTransaction.setBankId(paymentTransaction.getBankId());
                }
                if (paymentTransaction.getAmount() != null) {
                    existingPaymentTransaction.setAmount(paymentTransaction.getAmount());
                }

                return existingPaymentTransaction;
            })
            .map(paymentTransactionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentTransaction.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-transactions} : get all the paymentTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentTransactions in body.
     */
    @GetMapping("")
    public List<PaymentTransaction> getAllPaymentTransactions() {
        log.debug("REST request to get all PaymentTransactions");
        return paymentTransactionRepository.findAll();
    }

    /**
     * {@code GET  /payment-transactions/:id} : get the "id" paymentTransaction.
     *
     * @param id the id of the paymentTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentTransaction> getPaymentTransaction(@PathVariable Long id) {
        log.debug("REST request to get PaymentTransaction : {}", id);
        Optional<PaymentTransaction> paymentTransaction = paymentTransactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentTransaction);
    }

    /**
     * {@code DELETE  /payment-transactions/:id} : delete the "id" paymentTransaction.
     *
     * @param id the id of the paymentTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentTransaction(@PathVariable Long id) {
        log.debug("REST request to delete PaymentTransaction : {}", id);
        paymentTransactionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

package org.ptit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ptit.IntegrationTest;
import org.ptit.domain.PaymentTransaction;
import org.ptit.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentTransactionResourceIT {

    private static final String DEFAULT_BANK_ID = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/payment-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentTransactionMockMvc;

    private PaymentTransaction paymentTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTransaction createEntity(EntityManager em) {
        PaymentTransaction paymentTransaction = new PaymentTransaction().bankId(DEFAULT_BANK_ID).amount(DEFAULT_AMOUNT);
        return paymentTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTransaction createUpdatedEntity(EntityManager em) {
        PaymentTransaction paymentTransaction = new PaymentTransaction().bankId(UPDATED_BANK_ID).amount(UPDATED_AMOUNT);
        return paymentTransaction;
    }

    @BeforeEach
    public void initTest() {
        paymentTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentTransaction() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionRepository.findAll().size();
        // Create the PaymentTransaction
        restPaymentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTransaction testPaymentTransaction = paymentTransactionList.get(paymentTransactionList.size() - 1);
        assertThat(testPaymentTransaction.getBankId()).isEqualTo(DEFAULT_BANK_ID);
        assertThat(testPaymentTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createPaymentTransactionWithExistingId() throws Exception {
        // Create the PaymentTransaction with an existing ID
        paymentTransaction.setId(1L);

        int databaseSizeBeforeCreate = paymentTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        // Get all the paymentTransactionList
        restPaymentTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankId").value(hasItem(DEFAULT_BANK_ID)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getPaymentTransaction() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        // Get the paymentTransaction
        restPaymentTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTransaction.getId().intValue()))
            .andExpect(jsonPath("$.bankId").value(DEFAULT_BANK_ID))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentTransaction() throws Exception {
        // Get the paymentTransaction
        restPaymentTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentTransaction() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();

        // Update the paymentTransaction
        PaymentTransaction updatedPaymentTransaction = paymentTransactionRepository.findById(paymentTransaction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaymentTransaction are not directly saved in db
        em.detach(updatedPaymentTransaction);
        updatedPaymentTransaction.bankId(UPDATED_BANK_ID).amount(UPDATED_AMOUNT);

        restPaymentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentTransaction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentTransaction))
            )
            .andExpect(status().isOk());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
        PaymentTransaction testPaymentTransaction = paymentTransactionList.get(paymentTransactionList.size() - 1);
        assertThat(testPaymentTransaction.getBankId()).isEqualTo(UPDATED_BANK_ID);
        assertThat(testPaymentTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingPaymentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();
        paymentTransaction.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentTransaction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();
        paymentTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();
        paymentTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentTransactionWithPatch() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();

        // Update the paymentTransaction using partial update
        PaymentTransaction partialUpdatedPaymentTransaction = new PaymentTransaction();
        partialUpdatedPaymentTransaction.setId(paymentTransaction.getId());

        partialUpdatedPaymentTransaction.bankId(UPDATED_BANK_ID).amount(UPDATED_AMOUNT);

        restPaymentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentTransaction))
            )
            .andExpect(status().isOk());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
        PaymentTransaction testPaymentTransaction = paymentTransactionList.get(paymentTransactionList.size() - 1);
        assertThat(testPaymentTransaction.getBankId()).isEqualTo(UPDATED_BANK_ID);
        assertThat(testPaymentTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentTransactionWithPatch() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();

        // Update the paymentTransaction using partial update
        PaymentTransaction partialUpdatedPaymentTransaction = new PaymentTransaction();
        partialUpdatedPaymentTransaction.setId(paymentTransaction.getId());

        partialUpdatedPaymentTransaction.bankId(UPDATED_BANK_ID).amount(UPDATED_AMOUNT);

        restPaymentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentTransaction))
            )
            .andExpect(status().isOk());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
        PaymentTransaction testPaymentTransaction = paymentTransactionList.get(paymentTransactionList.size() - 1);
        assertThat(testPaymentTransaction.getBankId()).isEqualTo(UPDATED_BANK_ID);
        assertThat(testPaymentTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();
        paymentTransaction.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();
        paymentTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();
        paymentTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentTransaction in the database
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentTransaction() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransaction);

        int databaseSizeBeforeDelete = paymentTransactionRepository.findAll().size();

        // Delete the paymentTransaction
        restPaymentTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentTransaction> paymentTransactionList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

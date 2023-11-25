//package org.ptit.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import jakarta.persistence.EntityManager;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.ptit.IntegrationTest;
//import org.ptit.domain.Seller;
//import org.ptit.repository.SellerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link SellerResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class SellerResourceIT {
//
//    private static final Long DEFAULT_USER_ID = 1L;
//    private static final Long UPDATED_USER_ID = 2L;
//
//    private static final String ENTITY_API_URL = "/api/sellers";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private SellerRepository sellerRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restSellerMockMvc;
//
//    private Seller seller;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Seller createEntity(EntityManager em) {
//        Seller seller = new Seller().userId(DEFAULT_USER_ID);
//        return seller;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Seller createUpdatedEntity(EntityManager em) {
//        Seller seller = new Seller().user(UPDATED_USER_ID);
//        return seller;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        seller = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createSeller() throws Exception {
//        int databaseSizeBeforeCreate = sellerRepository.findAll().size();
//        // Create the Seller
//        restSellerMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seller)))
//            .andExpect(status().isCreated());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeCreate + 1);
//        Seller testSeller = sellerList.get(sellerList.size() - 1);
//        assertThat(testSeller.getUser()).isEqualTo(DEFAULT_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void createSellerWithExistingId() throws Exception {
//        // Create the Seller with an existing ID
//        seller.setId(1L);
//
//        int databaseSizeBeforeCreate = sellerRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restSellerMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seller)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllSellers() throws Exception {
//        // Initialize the database
//        sellerRepository.saveAndFlush(seller);
//
//        // Get all the sellerList
//        restSellerMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(seller.getId().intValue())))
//            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
//    }
//
//    @Test
//    @Transactional
//    void getSeller() throws Exception {
//        // Initialize the database
//        sellerRepository.saveAndFlush(seller);
//
//        // Get the seller
//        restSellerMockMvc
//            .perform(get(ENTITY_API_URL_ID, seller.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(seller.getId().intValue()))
//            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingSeller() throws Exception {
//        // Get the seller
//        restSellerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingSeller() throws Exception {
//        // Initialize the database
//        sellerRepository.saveAndFlush(seller);
//
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//
//        // Update the seller
//        Seller updatedSeller = sellerRepository.findById(seller.getId()).orElseThrow();
//        // Disconnect from session so that the updates on updatedSeller are not directly saved in db
//        em.detach(updatedSeller);
//        updatedSeller.userId(UPDATED_USER_ID);
//
//        restSellerMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedSeller.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedSeller))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//        Seller testSeller = sellerList.get(sellerList.size() - 1);
//        assertThat(testSeller.getUserId()).isEqualTo(UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingSeller() throws Exception {
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//        seller.setId(longCount.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restSellerMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, seller.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(seller))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchSeller() throws Exception {
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//        seller.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restSellerMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(seller))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamSeller() throws Exception {
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//        seller.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restSellerMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seller)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateSellerWithPatch() throws Exception {
//        // Initialize the database
//        sellerRepository.saveAndFlush(seller);
//
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//
//        // Update the seller using partial update
//        Seller partialUpdatedSeller = new Seller();
//        partialUpdatedSeller.setId(seller.getId());
//
//        partialUpdatedSeller.userId(UPDATED_USER_ID);
//
//        restSellerMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedSeller.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeller))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//        Seller testSeller = sellerList.get(sellerList.size() - 1);
//        assertThat(testSeller.getUserId()).isEqualTo(UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateSellerWithPatch() throws Exception {
//        // Initialize the database
//        sellerRepository.saveAndFlush(seller);
//
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//
//        // Update the seller using partial update
//        Seller partialUpdatedSeller = new Seller();
//        partialUpdatedSeller.setId(seller.getId());
//
//        partialUpdatedSeller.userId(UPDATED_USER_ID);
//
//        restSellerMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedSeller.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeller))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//        Seller testSeller = sellerList.get(sellerList.size() - 1);
//        assertThat(testSeller.getUserId()).isEqualTo(UPDATED_USER_ID);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingSeller() throws Exception {
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//        seller.setId(longCount.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restSellerMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, seller.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(seller))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchSeller() throws Exception {
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//        seller.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restSellerMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(seller))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamSeller() throws Exception {
//        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();
//        seller.setId(longCount.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restSellerMockMvc
//            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seller)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Seller in the database
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteSeller() throws Exception {
//        // Initialize the database
//        sellerRepository.saveAndFlush(seller);
//
//        int databaseSizeBeforeDelete = sellerRepository.findAll().size();
//
//        // Delete the seller
//        restSellerMockMvc
//            .perform(delete(ENTITY_API_URL_ID, seller.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Seller> sellerList = sellerRepository.findAll();
//        assertThat(sellerList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}

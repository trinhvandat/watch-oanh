package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.CustomerTestSamples.*;
import static org.ptit.domain.ProductTestSamples.*;
import static org.ptit.domain.ReviewTestSamples.*;

import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class ReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review.class);
        Review review1 = getReviewSample1();
        Review review2 = new Review();
        assertThat(review1).isNotEqualTo(review2);

        review2.setId(review1.getId());
        assertThat(review1).isEqualTo(review2);

        review2 = getReviewSample2();
        assertThat(review1).isNotEqualTo(review2);
    }

    @Test
    void customerTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        review.setCustomer(customerBack);
        assertThat(review.getCustomer()).isEqualTo(customerBack);

        review.customer(null);
        assertThat(review.getCustomer()).isNull();
    }

    @Test
    void productTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        review.setProduct(productBack);
        assertThat(review.getProduct()).isEqualTo(productBack);

        review.product(null);
        assertThat(review.getProduct()).isNull();
    }
}

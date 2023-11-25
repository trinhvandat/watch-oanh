package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.CartItemTestSamples.*;
import static org.ptit.domain.CategoryTestSamples.*;
import static org.ptit.domain.OrderTestSamples.*;
import static org.ptit.domain.ProductTestSamples.*;
import static org.ptit.domain.ReviewTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void reviewTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        product.addReview(reviewBack);
        assertThat(product.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getProduct()).isEqualTo(product);

        product.removeReview(reviewBack);
        assertThat(product.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getProduct()).isNull();

        product.reviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(product.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getProduct()).isEqualTo(product);

        product.setReviews(new HashSet<>());
        assertThat(product.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getProduct()).isNull();
    }

    @Test
    void categoryTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        product.setCategory(categoryBack);
        assertThat(product.getCategory()).isEqualTo(categoryBack);

        product.category(null);
        assertThat(product.getCategory()).isNull();
    }

    @Test
    void cartItemTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        CartItem cartItemBack = getCartItemRandomSampleGenerator();

        product.setCartItem(cartItemBack);
        assertThat(product.getCartItem()).isEqualTo(cartItemBack);
        assertThat(cartItemBack.getProduct()).isEqualTo(product);

        product.cartItem(null);
        assertThat(product.getCartItem()).isNull();
        assertThat(cartItemBack.getProduct()).isNull();
    }

    @Test
    void orderTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        product.setOrder(orderBack);
        assertThat(product.getOrder()).isEqualTo(orderBack);

        product.order(null);
        assertThat(product.getOrder()).isNull();
    }
}

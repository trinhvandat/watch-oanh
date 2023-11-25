package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.CartTestSamples.*;
import static org.ptit.domain.CustomerTestSamples.*;
import static org.ptit.domain.OrderTestSamples.*;
import static org.ptit.domain.PaymentTransactionTestSamples.*;
import static org.ptit.domain.ReviewTestSamples.*;

import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class CustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void cartTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        Cart cartBack = getCartRandomSampleGenerator();

        customer.setCart(cartBack);
        assertThat(customer.getCart()).isEqualTo(cartBack);
        assertThat(cartBack.getCustomer()).isEqualTo(customer);

        customer.cart(null);
        assertThat(customer.getCart()).isNull();
        assertThat(cartBack.getCustomer()).isNull();
    }

    @Test
    void orderTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        customer.setOrder(orderBack);
        assertThat(customer.getOrder()).isEqualTo(orderBack);
        assertThat(orderBack.getCustomer()).isEqualTo(customer);

        customer.order(null);
        assertThat(customer.getOrder()).isNull();
        assertThat(orderBack.getCustomer()).isNull();
    }

    @Test
    void paymentTransactionTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        PaymentTransaction paymentTransactionBack = getPaymentTransactionRandomSampleGenerator();

        customer.setPaymentTransaction(paymentTransactionBack);
        assertThat(customer.getPaymentTransaction()).isEqualTo(paymentTransactionBack);

        customer.paymentTransaction(null);
        assertThat(customer.getPaymentTransaction()).isNull();
    }

    @Test
    void reviewTest() throws Exception {
        Customer customer = getCustomerRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        customer.setReview(reviewBack);
        assertThat(customer.getReview()).isEqualTo(reviewBack);
        assertThat(reviewBack.getCustomer()).isEqualTo(customer);

        customer.review(null);
        assertThat(customer.getReview()).isNull();
        assertThat(reviewBack.getCustomer()).isNull();
    }
}

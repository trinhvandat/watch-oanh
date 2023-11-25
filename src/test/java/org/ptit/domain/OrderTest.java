package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.CustomerTestSamples.*;
import static org.ptit.domain.InvoiceTestSamples.*;
import static org.ptit.domain.OrderTestSamples.*;
import static org.ptit.domain.PaymentTransactionTestSamples.*;
import static org.ptit.domain.ProductTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class OrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = getOrderSample1();
        Order order2 = new Order();
        assertThat(order1).isNotEqualTo(order2);

        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);

        order2 = getOrderSample2();
        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    void customerTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        order.setCustomer(customerBack);
        assertThat(order.getCustomer()).isEqualTo(customerBack);

        order.customer(null);
        assertThat(order.getCustomer()).isNull();
    }

    @Test
    void productTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        order.addProduct(productBack);
        assertThat(order.getProducts()).containsOnly(productBack);
        assertThat(productBack.getOrder()).isEqualTo(order);

        order.removeProduct(productBack);
        assertThat(order.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getOrder()).isNull();

        order.products(new HashSet<>(Set.of(productBack)));
        assertThat(order.getProducts()).containsOnly(productBack);
        assertThat(productBack.getOrder()).isEqualTo(order);

        order.setProducts(new HashSet<>());
        assertThat(order.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getOrder()).isNull();
    }

    @Test
    void invoiceTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        order.setInvoice(invoiceBack);
        assertThat(order.getInvoice()).isEqualTo(invoiceBack);
        assertThat(invoiceBack.getOrder()).isEqualTo(order);

        order.invoice(null);
        assertThat(order.getInvoice()).isNull();
        assertThat(invoiceBack.getOrder()).isNull();
    }

    @Test
    void paymentTransactionTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        PaymentTransaction paymentTransactionBack = getPaymentTransactionRandomSampleGenerator();

        order.setPaymentTransaction(paymentTransactionBack);
        assertThat(order.getPaymentTransaction()).isEqualTo(paymentTransactionBack);
        assertThat(paymentTransactionBack.getOrder()).isEqualTo(order);

        order.paymentTransaction(null);
        assertThat(order.getPaymentTransaction()).isNull();
        assertThat(paymentTransactionBack.getOrder()).isNull();
    }
}

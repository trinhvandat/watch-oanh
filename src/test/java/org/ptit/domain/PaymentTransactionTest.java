package org.ptit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ptit.domain.CustomerTestSamples.*;
import static org.ptit.domain.OrderTestSamples.*;
import static org.ptit.domain.PaymentTransactionTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.ptit.web.rest.TestUtil;

class PaymentTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTransaction.class);
        PaymentTransaction paymentTransaction1 = getPaymentTransactionSample1();
        PaymentTransaction paymentTransaction2 = new PaymentTransaction();
        assertThat(paymentTransaction1).isNotEqualTo(paymentTransaction2);

        paymentTransaction2.setId(paymentTransaction1.getId());
        assertThat(paymentTransaction1).isEqualTo(paymentTransaction2);

        paymentTransaction2 = getPaymentTransactionSample2();
        assertThat(paymentTransaction1).isNotEqualTo(paymentTransaction2);
    }

    @Test
    void orderTest() throws Exception {
        PaymentTransaction paymentTransaction = getPaymentTransactionRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        paymentTransaction.setOrder(orderBack);
        assertThat(paymentTransaction.getOrder()).isEqualTo(orderBack);

        paymentTransaction.order(null);
        assertThat(paymentTransaction.getOrder()).isNull();
    }

    @Test
    void customerTest() throws Exception {
        PaymentTransaction paymentTransaction = getPaymentTransactionRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        paymentTransaction.addCustomer(customerBack);
        assertThat(paymentTransaction.getCustomers()).containsOnly(customerBack);
        assertThat(customerBack.getPaymentTransaction()).isEqualTo(paymentTransaction);

        paymentTransaction.removeCustomer(customerBack);
        assertThat(paymentTransaction.getCustomers()).doesNotContain(customerBack);
        assertThat(customerBack.getPaymentTransaction()).isNull();

        paymentTransaction.customers(new HashSet<>(Set.of(customerBack)));
        assertThat(paymentTransaction.getCustomers()).containsOnly(customerBack);
        assertThat(customerBack.getPaymentTransaction()).isEqualTo(paymentTransaction);

        paymentTransaction.setCustomers(new HashSet<>());
        assertThat(paymentTransaction.getCustomers()).doesNotContain(customerBack);
        assertThat(customerBack.getPaymentTransaction()).isNull();
    }
}

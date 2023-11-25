package org.ptit.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentTransactionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PaymentTransaction getPaymentTransactionSample1() {
        return new PaymentTransaction().id(1L).bankId("bankId1");
    }

    public static PaymentTransaction getPaymentTransactionSample2() {
        return new PaymentTransaction().id(2L).bankId("bankId2");
    }

    public static PaymentTransaction getPaymentTransactionRandomSampleGenerator() {
        return new PaymentTransaction().id(longCount.incrementAndGet()).bankId(UUID.randomUUID().toString());
    }
}

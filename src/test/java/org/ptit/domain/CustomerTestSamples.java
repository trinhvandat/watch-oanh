package org.ptit.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Customer getCustomerSample1() {
        return new Customer().id(1L).userId(1L);
    }

    public static Customer getCustomerSample2() {
        return new Customer().id(2L).userId(2L);
    }

    public static Customer getCustomerRandomSampleGenerator() {
        return new Customer().id(longCount.incrementAndGet()).userId(longCount.incrementAndGet());
    }
}

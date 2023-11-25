package org.ptit.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product().id(1L).name("name1").image("image1").inventory(1).sold(1);
    }

    public static Product getProductSample2() {
        return new Product().id(2L).name("name2").image("image2").inventory(2).sold(2);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .image(UUID.randomUUID().toString())
            .inventory(intCount.incrementAndGet())
            .sold(intCount.incrementAndGet());
    }
}

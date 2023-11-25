package org.ptit.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ReviewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Review getReviewSample1() {
        return new Review().id(1L).comment("comment1").rate(1);
    }

    public static Review getReviewSample2() {
        return new Review().id(2L).comment("comment2").rate(2);
    }

    public static Review getReviewRandomSampleGenerator() {
        return new Review().id(longCount.incrementAndGet()).comment(UUID.randomUUID().toString()).rate(intCount.incrementAndGet());
    }
}

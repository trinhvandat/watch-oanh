//package org.ptit.domain;
//
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//
//public class SellerTestSamples {
//
//    private static final Random random = new Random();
//    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    public static Seller getSellerSample1() {
//        return new Seller().id(1L).userId(1L);
//    }
//
//    public static Seller getSellerSample2() {
//        return new Seller().id(2L).userId(2L);
//    }
//
//    public static Seller getSellerRandomSampleGenerator() {
//        return new Seller().id(longCount.incrementAndGet()).userId(longCount.incrementAndGet());
//    }
//}

package com.infoshare.junit.automotive;

public class CarTester {

    public void testIgnition() throws Exception {
        Car sut = new CarFactory().forBrand(Brand.HONDA).build();
        sut.ignite();
        if (!sut.isRunning()) {
            throw new RuntimeException("Error! Car is not running after ignition");
        };
        System.out.println("Car is running after ignition");
    }

    public void testEmissionLevel() throws Exception {
        Car sut = new CarFactory().forBrand(Brand.VW).build();
        sut.ignite();
        if (sut.measurePollution()!=EmissionLevel.NORMAL) {
            throw new RuntimeException("Error! Emmission level is not normal");
        };
        System.out.println("Emmission level is normal");
    }
}
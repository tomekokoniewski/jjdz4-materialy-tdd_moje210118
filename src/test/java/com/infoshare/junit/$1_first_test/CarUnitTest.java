package com.infoshare.junit.$1_first_test;

import com.infoshare.junit.automotive.Brand;
import com.infoshare.junit.automotive.Car;
import com.infoshare.junit.automotive.CarFactory;
import com.infoshare.junit.automotive.EmissionLevel;
import org.junit.Assert;
import org.junit.Test;

public class CarUnitTest {

    @Test
    public void toyota_engine_should_be_running_after_ignition() throws Exception {
        Car sut = new CarFactory().forBrand(Brand.TOYOTA).build();
        sut.ignite();
        Assert.assertTrue("OMG! Car is not running after ignition", sut.isRunning());
    }

    @Test
    public void honda_emmission_level_should_be_normal() throws Exception {
        Car sut = new CarFactory().forBrand(Brand.HONDA).build();
        sut.ignite();
        Assert.assertTrue("emmission level should be normal", EmissionLevel.NORMAL==sut.measurePollution());
    }

    @Test
    public void vw_emmission_level_should_be_normal() throws Exception {
        Car sut = new CarFactory().forBrand(Brand.VW).build();
        sut.ignite();
        Assert.assertTrue("emmission level should be normal", EmissionLevel.NORMAL==sut.measurePollution());
    }

    public void toyota_emission_level_should_be_normal() throws Exception {
        // TODO
    }

    public void should_throw_exception_for_unknown_brands() throws Exception {
        // TODO
    }

}

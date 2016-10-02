package com.infoshare.junit.$1_first_test;

import com.infoshare.junit.automotive.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarUnitTest {

    @Test
    public void toyota_engine_should_be_running_after_ignition() throws Exception {
        // given
        Car sut = new CarFactory().forBrand(Brand.TOYOTA).build();
        // when
        sut.ignite();
        // then
        assertTrue("OMG! Car is not running after ignition", sut.isRunning());
    }

    @Test
    public void honda_engine_should_be_running_after_ignition() throws Exception {
        // given
        Car sut = new CarFactory().forBrand(Brand.HONDA).build();
        // when
        sut.ignite();
        // then
        assertTrue("OMG! Car is not running after ignition", sut.isRunning());
    }

    @Test
    public void honda_emmission_level_should_be_normal() throws Exception {
        // given
        Car sut = new CarFactory().forBrand(Brand.HONDA).build();
        // when
        sut.ignite();
        // then
        assertEquals("emmission level should be normal", EmissionLevel.NORMAL, sut.measurePollution());
    }

    @Ignore
    @Test
    public void vw_emmission_level_should_be_normal() throws Exception {
        // given
        Car sut = new CarFactory().forBrand(Brand.VW).build();
        // when
        sut.ignite();
        ((VW) sut).setLabMode(false);
        // then
        assertEquals("emmission level should be normal", EmissionLevel.NORMAL, sut.measurePollution());
    }

    @Test(expected = Exception.class)
    public void should_throw_exception_for_unknown_brands() throws Exception {
        // given
        Car sut = new CarFactory().forBrand(Brand.FSO).build();
    }

    @Ignore
    @Test(timeout = 500)
    public void toyota_emission_level_should_be_normal() throws Exception {
        // given
        Car sut = new CarFactory().forBrand(Brand.TOYOTA).build();
        // when
        sut.ignite();
        // then
        assertEquals("emmission level should be normal", EmissionLevel.NORMAL, sut.measurePollution());
    }

}

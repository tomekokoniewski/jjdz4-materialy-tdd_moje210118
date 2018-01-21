package com.infoshare.junit.$10_rover;

import com.infoshare.junit.rover.Direction;
import com.infoshare.junit.rover.Rover;
import com.infoshare.junit.rover.Wise;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class RoverTest {

    private Rover rover;

    @Before
    public void before() {
        rover = new Rover();
    }

    @Test
    @Parameters({
            "C, N, E",
            "CT, N, W",
            "C, W, N",
    })
    public void turningTest(Wise wise, Direction initDirection, Direction direction) {
        rover.setDirection(initDirection);
        rover.turn(wise);
        assertThat(rover.getDirection()).isEqualTo(direction);
    }

    @Test
    public void movementTest() {
        rover.move(2);
        assertThat(rover.getCoordinates()).isEqualTo(new int[]{2,0});
    }

    @Test
    public void turnAndMoveTest() {
        rover.execute("CCFFCFDFR");
        assertThat(rover.getCoordinates()).isEqualTo(new int[]{8,9});
    }

    @Test
    public void turnAndMoveOutputStringTest() {
        assertThat(rover.execute("CCFFCFDF")).isEqualTo("7:9:S");
    }
}

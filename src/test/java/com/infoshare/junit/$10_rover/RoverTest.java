package com.infoshare.junit.$10_rover;

import com.infoshare.junit.rover.Rover;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class RoverTest {

    @Test
    @Ignore
    public void should_turn_right_once() {
        Rover rover = new Rover();
        String position = rover.execute("R");
        assertThat(position).isEqualTo("0:0:E");
    }

    @Test
    @Ignore
    public void should_turn_right_twice() {
        Rover rover = new Rover();
        String position = rover.execute("R");
        assertThat(position).isEqualTo("0:0:E");
    }

    // tests above can be simplified using JUnitParams plugin

    @Test
    @Parameters({
            "R, 0:0:E",
            "RR, 0:0:S"
    })
    public void should_turn_right(String commands, String position) {
        Rover rover = new Rover();
        String result = rover.execute(commands);
        assertThat(result).isEqualTo(position);
    }
}

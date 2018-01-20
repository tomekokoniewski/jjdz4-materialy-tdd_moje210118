package com.infoshare.junit.$10_rover;

import com.infoshare.junit.rover.IffyRover;
import com.infoshare.junit.rover.RoverOutOfFieldException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IffyRoverTest {

    @Test
    public void testShouldMoveForward() {
        IffyRover rover = new IffyRover();
        rover.move("F");
        assertEquals(0, rover.getPositionX());
        assertEquals(1, rover.getPositionY());    }

    @Test
    public void testShouldTurn() {
        IffyRover rover = new IffyRover();
        rover.move("T");
        assertEquals("E", rover.getOrientation());
        rover.move("T");
        assertEquals("S", rover.getOrientation());
        rover.move("T");
        assertEquals("W", rover.getOrientation());
        rover.move("T");
        assertEquals("N", rover.getOrientation());
    }

    @Test
    public void testShouldTurnAndMoveInTheRightDirection() {
        IffyRover rover = new IffyRover();
        rover.move("T");
        rover.move("F");
        assertEquals(1, rover.getPositionX());
        assertEquals(0, rover.getPositionY());
        rover.move("T");
        rover.move("F");
        assertEquals(1, rover.getPositionX());
        assertEquals(-1, rover.getPositionY());
        rover.move("T");
        rover.move("F");
        assertEquals(0, rover.getPositionX());
        assertEquals(-1, rover.getPositionY());
        rover.move("T");
        rover.move("F");
        assertEquals(0, rover.getPositionX());
        assertEquals(0, rover.getPositionY());
    }

    @Test(expected = RoverOutOfFieldException.class)
    public void testShouldThrowExceptionIfFallsOffPlateau() {
        IffyRover rover = new IffyRover();
        rover.move("F");
        rover.move("F");
        rover.move("F");
        rover.move("F");
        rover.move("F");
        rover.move("F");
    }

}

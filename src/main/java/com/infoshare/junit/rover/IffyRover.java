package com.infoshare.junit.rover;

/**
 * TODO 1 refactor to OOP, remove if statements
 * TODO 2 add new command "R" that moves rover in reverse direction
 * TODO 3 add new command "CT" that rotates rover counterclockwise
 */
public class IffyRover {

    int[] position = new int[]{0, 0};
    int[] field = new int[]{5, 5};
    String orientation = "N";

    public void move(String command) {
        if (command == "F") {
            moveForwards();
        } else if (command == "T") {
            turnClockwise();
        }
    }

    private void moveForwards() {
        if (orientation == "N") {
            position = new int[]{position[0], position[1] + 1};
        } else if (orientation == "E") {
            position = new int[]{position[0] + 1, position[1]};
        } else if (orientation == "S") {
            position = new int[]{position[0], position[1] - 1};
        } else if (orientation == "W") {
            position = new int[]{position[0] - 1, position[1]};
        }

        if (position[0] > field[0] || position[1] > field[1]) {
            throw new RoverOutOfFieldException();
        }
    }

    private void turnClockwise() {
        if (orientation == "N") {
            orientation = "E";
        } else if (orientation == "E") {
            orientation = "S";
        } else if (orientation == "S") {
            orientation = "W";
        } else if (orientation == "W") {
            orientation = "N";
        }
    }

    public int getPositionX() {
        return position[0];
    }

    public int getPositionY() {
        return position[1];
    }

    public String getOrientation() {
        return orientation;
    }
}

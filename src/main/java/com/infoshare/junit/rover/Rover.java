        package com.infoshare.junit.rover;

        import java.util.Arrays;

public class Rover {

    private Direction[] directions = new Direction[]{Direction.N,Direction.E,Direction.S,Direction.W};
    private Direction direction = Direction.N;
    private Field field = new Field(10,10);

    public void turn(Wise wise) {
        int index = Arrays.asList(directions).indexOf(direction);
        if (wise.equals(Wise.C)) {
            index = (index == 3)? 0 : ++index;
        } else if (wise.equals(Wise.CT)) {
            index = (index == 0)? 3 : --index;
        }
        direction = directions[index];
    }

    public void move(int steps) {
        switch (direction) {
            case N :
                field.changeX(steps);
                break;
            case E :
                field.changeY(steps);
                break;
            case S :
                field.changeX(-steps);
                break;
            case W :
                field.changeY(-steps);
        }
    }

    public String execute(String commands) {
        byte[] com = commands.getBytes();
        for (byte i : com) {
            switch (i) {
                case 'F' :
                    move(1);
                    break;
                case 'R' :
                    move(-1);
                case 'C' :
                    turn(Wise.C);
                    break;
                case 'D' :
                    turn(Wise.CT);
            }
        }
        return this.getX() + ":" + this.getY() + ":" + this.getDirection();
    }

    public int getX() {
        return field.getX();
    }

    public int getY() {
        return field.getY();
    }

    public int[] getCoordinates() {
        return field.getCoordinates();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
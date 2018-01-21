
        package com.infoshare.junit.rover;

public class Field {

    private final int limitX;
    private final int limitY;
    private int[] field = new int[]{0,0};

    public Field(int x, int y) {
        this.limitX = x;
        this.limitY = y;

    }

    public void changeX(int x) {
        int newX = field[0]+x;
        if (x > 0){
            field[0] = (newX > limitX)? newX - limitX : newX;
        } else field[0] = (newX < 0)? limitX + newX : newX;
    }

    public void changeY(int y) {
        int newY = field[1]+y;
        if (y > 0){
            field[1] = (newY > limitY)? newY - limitY : newY;
        } else field[1] = (newY < 0)? limitY + newY : newY;
    }

    public int getX() {
        return field[0];
    }

    public int getY() {
        return field[1];
    }

    public int[] getCoordinates() {
        return field;
    }
}
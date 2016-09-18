package com.infoshare.junit.automotive;

public class CarTesterApp {

    public static void main(String[] args) throws Exception {
        CarTester carTester = new CarTester();
        carTester.testEmissionLevel();
        carTester.testIgnition();

        System.out.println("-                      -");
        System.out.println("Car is ready for a drive");
        System.out.println("-                      -");
    }
}

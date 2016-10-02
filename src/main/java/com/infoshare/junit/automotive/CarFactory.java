package com.infoshare.junit.automotive;

public class CarFactory {
    private Brand brand;

    public CarFactory forBrand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public Car build() throws Exception {
        switch(brand) {
            case VW:
                return new VW();
            case HONDA:
                return new Honda();
            case TOYOTA:
                return new Toyota();
        }
        throw new Exception("Unsupported brand " + this.brand);
    }

}
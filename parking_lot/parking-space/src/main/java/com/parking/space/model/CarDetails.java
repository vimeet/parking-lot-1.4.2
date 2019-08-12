package com.parking.space.model;

public class CarDetails {
    private String carNumber;

    public CarDetails(String carNumber, String carColor) {
        this.carNumber = carNumber;
        this.carColor = carColor;
    }

    private String carColor;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
}

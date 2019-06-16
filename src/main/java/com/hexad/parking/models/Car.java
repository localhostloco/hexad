package com.hexad.parking.models;

public class Car {
  private String color;
  private String plate;

  public Car(String plate, String color) {
    this.color = color;
    this.plate = plate;
  }

  public String getColor() {
    return color;
  }

  public String getPlate() {
    return plate;
  }

  public boolean isCarThisColor(String color) {
    return this.color.equals(color);
  }
}

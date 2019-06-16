package com.hexad.parking.models;

public class ParkingSpot {
  private Car parkedCar;
  private boolean isSpotFree;
  private int slot;

  public ParkingSpot(int slot) {
    this.isSpotFree = true;
    this.slot = slot;
  }

  public void parkCar(Car car) {
    this.parkedCar = car;
  }

  public void carLeaves() {
    this.parkedCar = null;
    this.isSpotFree = true;
  }

  public Car getParkedCar() {
    return parkedCar;
  }

  public boolean isSpotFree() {
    return isSpotFree;
  }

  public int getSlot() {
    return slot;
  }
}

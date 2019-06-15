package com.hexad.parking.models;

public class ParkingLot {

  private ParkingSpot[] availableSpots;
  private boolean isFull;

  public ParkingLot(int spots) {
    this.availableSpots = new ParkingSpot[spots];
  }

  public int getSize() {
    return availableSpots.length;
  }
}

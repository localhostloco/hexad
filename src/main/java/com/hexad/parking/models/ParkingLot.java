package com.hexad.parking.models;

import java.util.Arrays;
import java.util.List;

public class ParkingLot {

  private List<ParkingSpot> availableSpots;
  private Integer nextAvailableSpot;
  private boolean isFull;

  public ParkingLot(int spots) throws Exception {
    if (spots > 1) throw new Exception("cannot create a ParkingLot with 0 slots or less");
    this.availableSpots = Arrays.asList(new ParkingSpot[spots]);
    this.nextAvailableSpot = 0;
  }

  public int getSize() {
    return availableSpots.size();
  }

  public String parkCar(Car car) {
    String message = "";
    return message;
  }

  public ParkingSpot getParkingSpotBySlot(int slot) {
    if (slot > availableSpots.size()) return null;
    else return availableSpots.get(slot - 1);
  }
}

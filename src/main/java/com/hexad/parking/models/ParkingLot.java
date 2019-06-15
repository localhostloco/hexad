package com.hexad.parking.models;

import java.util.Arrays;
import java.util.List;

public class ParkingLot {

  private List<ParkingSpot> availableSpots;
  private boolean isFull;

  public ParkingLot(int spots) {
    this.availableSpots = Arrays.asList(new ParkingSpot[spots]);
  }

  public int getSize() {
    return availableSpots.size();
  }

  public ParkingSpot getParkingSpotBySlot(int slot) {
    if (slot > availableSpots.size()) return null;
    else return availableSpots.get(slot - 1);
  }
}

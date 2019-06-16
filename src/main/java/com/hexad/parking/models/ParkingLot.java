package com.hexad.parking.models;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ParkingLot {

  private List<ParkingSpot> availableSpots;
  private Integer nextAvailableSpot;
  private boolean isFull;

  public ParkingLot(int spots) throws Exception {
    if (spots < 1) throw new Exception("cannot create a ParkingLot with 0 slots or less");
    this.availableSpots = Arrays.asList(new ParkingSpot[spots]);
    for (int i = 0; i < availableSpots.size(); i++) availableSpots.set(i, new ParkingSpot(i));
    this.nextAvailableSpot = 0;
    this.isFull = false;
  }

  public int getSize() {
    return availableSpots.size();
  }

  public String parkCar(Car car) {
    String message;
    if (isFull) message = "Sorry, parking lot is full";
    else {
      ParkingSpot parkingSpot = getParkingSpotBySlot(nextAvailableSpot);
      parkingSpot.parkCar(car);
      message = String.format("Allocated slot number: %d", nextAvailableSpot + 1);
      calculateNextAvailableSpot();
    }
    return message;
  }

  public ParkingSpot getParkingSpotBySlot(int slot) {
    if (slot > availableSpots.size()) return null;
    else return availableSpots.get(slot);
  }

  public String carLeaves(int spot) throws Exception {
    if (spot > availableSpots.size())
      throw new Exception("there is no such spot in the current ParkingLot");
    String message = String.format("Slot number %d is free", spot);
    spot -= 1;
    ParkingSpot parkingSpot = availableSpots.get(spot);
    parkingSpot.carLeaves();
    if (null == nextAvailableSpot || nextAvailableSpot > spot) nextAvailableSpot = spot;
    else calculateNextAvailableSpot();
    isFull = false;
    return message;
  }

  private void calculateNextAvailableSpot() {
    Optional<ParkingSpot> freeSpot =
        availableSpots.stream().filter(ParkingSpot::isSpotFree).findFirst();
    isFull = !freeSpot.isPresent();
    if (isFull) {
      nextAvailableSpot = null;
    } else nextAvailableSpot = freeSpot.get().getSlot();
  }

  public List<ParkingSpot> getAvailableSpots() {
    return availableSpots;
  }
}

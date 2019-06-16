package com.hexad.parking.facades;

import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;
import com.hexad.parking.models.ParkingSpot;

import java.util.Optional;

public class ParkingLotFacadeImpl implements ParkingLotFacade {
  public ParkingLot createParkingLot(int spots) {
    System.out.println(String.format("Created a parking lot with %d slots", spots));
    try {
      return new ParkingLot(spots);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void parkCar(ParkingLot parkingLot, Car car) {
    System.out.println(parkingLot.parkCar(car));
  }

  public void carLeaves(ParkingLot parkingLot, int spot) {
    try {
      System.out.println(parkingLot.carLeaves(spot));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void printStatus(ParkingLot parkingLot) {
    String status = "No parked cars yet, try again later";
    Optional<ParkingSpot> atLeastOneCar =
        parkingLot.getAvailableSpots().stream().filter(spot -> !spot.isSpotFree()).findAny();
    if (atLeastOneCar.isPresent()) {
      System.out.print("Slot No.\tRegistration No\tColour\r");
      parkingLot.getAvailableSpots().stream().forEachOrdered(this::printParkedCarInfo);
      System.out.println();
    } else System.out.println(status);
  }

  private void printParkedCarInfo(ParkingSpot parkingSpot) {
    if (!parkingSpot.isSpotFree())
      System.out.print(
          String.format(
              "\n%d\t%s\t%s",
              parkingSpot.getSlot() + 1,
              parkingSpot.getParkedCar().getPlate(),
              parkingSpot.getParkedCar().getColor()));
  }
}

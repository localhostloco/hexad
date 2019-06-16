package com.hexad.parking.facades;

import com.hexad.parking.exceptions.ParkingException;
import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;
import com.hexad.parking.models.ParkingSpot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParkingLotFacadeImpl implements ParkingLotFacade {

  private static final String NOT_FOUND = "not found";

  public ParkingLot createParkingLot(int spots) {
    System.out.println(String.format("Created a parking lot with %d slots", spots));
    try {
      return new ParkingLot(spots);
    } catch (ParkingException e) {
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
    } catch (ParkingException e) {
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
              "%n%d\t%s\t%s",
              parkingSpot.getSlot() + 1,
              parkingSpot.getParkedCar().getPlate(),
              parkingSpot.getParkedCar().getColor()));
  }

  public void getPlatesByColor(ParkingLot parkingLot, String color) {
    List<String> plates =
        parkingLot.getAvailableSpots().stream()
            .filter(parkingSpot -> !parkingSpot.isSpotFree())
            .filter(parkingSpot -> parkingSpot.getParkedCar().isCarThisColor(color))
            .map(parkingSpot -> parkingSpot.getParkedCar().getPlate())
            .collect(Collectors.toList());
    if (plates.isEmpty()) System.out.println(String.format("%s cars not found", color));
    else {
      boolean first = true;
      for (String plate : plates) {
        if (first) {
          first = false;
          System.out.print(plate);
        } else System.out.print(String.format(", %s", plate));
      }
      System.out.println();
    }
  }

  public void getSlotByPlates(ParkingLot parkingLot, String plate) {
    Optional<ParkingSpot> spot =
        parkingLot.getAvailableSpots().stream()
            .filter(parkingSpot -> !parkingSpot.isSpotFree())
            .filter(parkingSpot -> parkingSpot.getParkedCar().isPlateThis(plate))
            .findAny();
    if (spot.isPresent()) System.out.println(spot.get().getSlot() + 1);
    else System.out.println(String.format(NOT_FOUND, plate));
  }

  public void getSlotsByColor(ParkingLot parkingLot, String color) {
    List<Integer> spots =
        parkingLot.getAvailableSpots().stream()
            .filter(parkingSpot -> !parkingSpot.isSpotFree())
            .filter(parkingSpot -> parkingSpot.getParkedCar().isCarThisColor(color))
            .map(ParkingSpot::getSlot)
            .collect(Collectors.toList());
    if (spots.isEmpty()) System.out.println(String.format("%s cars %s", color, NOT_FOUND));
    else {
      boolean first = true;
      for (Integer spot : spots) {
        spot += 1;
        if (first) {
          first = false;
          System.out.print(spot);
        } else System.out.print(String.format(", %s", spot));
      }
      System.out.println();
    }
  }
}

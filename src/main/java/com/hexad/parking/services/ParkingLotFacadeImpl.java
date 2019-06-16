package com.hexad.parking.services;

import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;

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
}

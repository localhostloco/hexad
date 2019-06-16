package com.hexad.parking.services;

import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;

public class ParkingLotServiceImpl implements ParkingLotService {
  @Override
  public ParkingLot createParkingLot(int spots) {
    System.out.println(String.format("Created a parking lot with %d slots", spots));
    try {
      return new ParkingLot(spots);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void parkCar(ParkingLot parkingLot, Car car) {
    System.out.println(parkingLot.parkCar(car));
  }
}

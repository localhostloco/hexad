package com.hexad.parking.services;

import com.hexad.parking.models.ParkingLot;

public class ParkingLotServiceImpl implements ParkingLotService {
  @Override
  public ParkingLot createParkingLot(int spots) {
    System.out.println(String.format("Created a parking lot with %d slots", spots));
    return new ParkingLot(spots);
  }
}

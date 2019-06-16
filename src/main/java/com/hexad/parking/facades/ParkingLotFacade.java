package com.hexad.parking.facades;

import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;

public interface ParkingLotFacade {

  ParkingLot createParkingLot(int spots);

  void parkCar(ParkingLot parkingLot, Car car);

  void carLeaves(ParkingLot parkingLot, int spot);
}
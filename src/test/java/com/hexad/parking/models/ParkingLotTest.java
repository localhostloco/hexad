package com.hexad.parking.models;

import com.hexad.parking.exceptions.ParkingException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {

  @Test
  public void zeroSpotsOnCreateParkingLotThrowsException() throws ParkingException {
    int zeroSpots = 0;
    assertThrows(ParkingException.class, () -> new ParkingLot(zeroSpots));
  }

  @Test
  public void unreachableSpotToLeaveThrowsException() throws ParkingException {
    int oneSpot = 1;
    int twoSpots = 2;
    ParkingLot parkingLot = new ParkingLot(oneSpot);
    assertThrows(ParkingException.class, () -> parkingLot.carLeaves(twoSpots));
  }
}

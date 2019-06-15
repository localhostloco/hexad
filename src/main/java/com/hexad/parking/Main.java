package com.hexad.parking;

import com.hexad.parking.models.ParkingLot;
import org.junit.Assert;

public class Main {

  private static ParkingLot parkingLot;

  public static void main(String[] args) {
    Assert.assertNotNull(args);
    // file input
    if (args.length > 0) {

    }
    // interactive mode
    else {

    }
  }

  public static ParkingLot getParkingLot() {
    return parkingLot;
  }
}

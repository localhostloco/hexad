package com.hexad.parking.helpers;

import com.hexad.parking.exceptions.ParkingException;
import com.hexad.parking.models.ParkingLot;
import org.junit.Test;

import static com.hexad.parking.helpers.CommandHandler.handleCreateParkingLot;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandHandlerTest {

  @Test
  public void cannotCreateMoreThanOneParkingLot() throws ParkingException {
    int oneSpot = 1;
    assertThrows(
        ParkingException.class,
        () -> handleCreateParkingLot(new ParkingLot(oneSpot), new String[oneSpot]));
  }
}

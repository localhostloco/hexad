package com.hexad.parking;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

  @Test
  public void nullArgumentsThrowAssertionError() {
    assertThrows(AssertionError.class, () -> Main.main(null));
  }

  @Test
  public void canCreateParkingLot() {}
}

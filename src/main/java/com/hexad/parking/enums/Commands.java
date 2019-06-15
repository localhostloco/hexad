package com.hexad.parking.enums;

import java.util.Arrays;

public enum Commands {
  create_parking_lot(1),
  park(2),
  leave(1),
  status(0),
  registration_numbers_for_cars_with_colour(1),
  slot_numbers_for_cars_with_colour(1),
  slot_number_for_registration_number(1);

  private int numberOfArgs;

  Commands(int args) {
    this.numberOfArgs = args;
  }

  public int getNumberOfArgs() {
    return this.numberOfArgs;
  }

  public static Commands findByCommand(final String command) {
    return Arrays.stream(values())
        .filter(value -> value.name().equals(command))
        .findFirst()
        .orElse(null);
  }
}

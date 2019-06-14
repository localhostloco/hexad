package com.hexad.parking.enums;

public enum Commands {
  CREATE_PARKING("create_parking_lot"),
  PARK("park"),
  LEAVE("leave"),
  STATUS("status"),
  PLATES_BY_COLOR("registration_numbers_for_cars_with_colour"),
  SLOTS_BY_COLOR("slot_numbers_for_cars_with_colour"),
  SLOT_BY_PLATES("slot_number_for_registration_number");

  private String command;

  Commands(String command) {
    this.command = command;
  }
}

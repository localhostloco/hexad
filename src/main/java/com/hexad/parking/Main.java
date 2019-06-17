package com.hexad.parking;

import com.hexad.parking.enums.Commands;
import com.hexad.parking.exceptions.ParkingException;
import com.hexad.parking.models.ParkingLot;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.hexad.parking.helpers.CommandHandler.*;

public class Main {

  private static ParkingLot parkingLot;

  public static void main(String[] args) throws ParkingException, IOException {
    Assert.assertNotNull(args);
    // file input
    if (args.length > 0) {
      try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
        stream.forEachOrdered(
            line -> {
              try {
                interpretCommand(line);
              } catch (ParkingException e) {
                e.printStackTrace();
              }
            });
      }
    }
    // interactive mode
    else {
      Scanner scanner = new Scanner(System.in);
      while (scanner.hasNext()) {
        interpretCommand(scanner.nextLine());
      }
    }
  }

  private static String[] interpretCommand(String line) throws ParkingException {
    String[] commandAndArgs = line.split(" ");
    Commands comm = Commands.findByCommand(commandAndArgs[0]);
    if (null == comm)
      throw new UnsupportedOperationException(
          String.format("%s is not supported at the moment", commandAndArgs[0]));
    switch (comm) {
      case create_parking_lot:
        handleCreateParkingLot(getParkingLot(), commandAndArgs);
        break;
      case park:
        handleParkCar(getParkingLot(), commandAndArgs);
        break;
      case leave:
        handleCarLeaves(getParkingLot(), commandAndArgs);
        break;
      case status:
        handlePrintStatus(getParkingLot(), commandAndArgs);
        break;
      case registration_numbers_for_cars_with_colour:
        handlePlatesByColor(getParkingLot(), commandAndArgs);
        break;
      case slot_number_for_registration_number:
        handleSlotByCarPlates(getParkingLot(), commandAndArgs);
        break;
      case slot_numbers_for_cars_with_colour:
        handleSlotsByColor(getParkingLot(), commandAndArgs);
        break;
      default:
    }
    return commandAndArgs;
  }

  public static ParkingLot getParkingLot() {
    return parkingLot;
  }

  public static void setParkingLot(ParkingLot parkingLot) {
    Main.parkingLot = parkingLot;
  }

  protected static void restart() {
    parkingLot = null;
  }
}

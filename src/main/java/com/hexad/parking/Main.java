package com.hexad.parking;

import com.hexad.parking.enums.Commands;
import com.hexad.parking.models.ParkingLot;
import com.hexad.parking.services.ParkingLotService;
import com.hexad.parking.services.ParkingLotServiceImpl;
import org.junit.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.hexad.parking.enums.Commands.create_parking_lot;

public class Main {

  private static String invalidNumberOfArgs = "invalid number of arguments (expected: %d)";
  private static ParkingLotService parkingLotService;
  private static ParkingLot parkingLot;

  public static void main(String[] args) {
    Assert.assertNotNull(args);
    parkingLotService = new ParkingLotServiceImpl();
    // file input
    if (args.length > 0) {
      try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
        stream.forEachOrdered(
            line -> {
              try {
                interpretCommand(line);
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // interactive mode
    else {
      Scanner scanner = new Scanner(System.in);
      while (scanner.hasNext()) {
        try {
          interpretCommand(scanner.nextLine());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private static String[] interpretCommand(String line) throws Exception {
    String[] commandAndArgs = line.split(" ");
    Commands comm = Commands.findByCommand(commandAndArgs[0]);
    if (null == comm)
      throw new UnsupportedOperationException(
          String.format("%s is not supported at the moment", commandAndArgs[0]));
    switch (comm) {
      case create_parking_lot:
        handleCreateParkingLot(commandAndArgs);
        break;
      default:
        break;
    }
    return commandAndArgs;
  }

  private static void handleCreateParkingLot(String[] commandAndArgs) throws Exception {
    if (null != getParkingLot()) throw new Exception("ParkingLot already created!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = create_parking_lot.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLot = parkingLotService.createParkingLot(Integer.valueOf(commandAndArgs[1]));
  }

  public static ParkingLot getParkingLot() {
    return parkingLot;
  }
}

package com.hexad.parking;

import com.hexad.parking.enums.Commands;
import com.hexad.parking.facades.ParkingLotFacade;
import com.hexad.parking.facades.ParkingLotFacadeImpl;
import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;
import org.junit.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.hexad.parking.enums.Commands.*;

public class Main {

  private static String invalidNumberOfArgs = "invalid number of arguments (expected: %d)";
  private static ParkingLotFacade parkingLotFacade;
  private static ParkingLot parkingLot;

  public static void main(String[] args) {
    Assert.assertNotNull(args);
    parkingLotFacade = new ParkingLotFacadeImpl();
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
      case park:
        handleParkCar(commandAndArgs);
        break;
      case leave:
        handleCarLeaves(commandAndArgs);
        break;
      case status:
        handlePrintStatus(commandAndArgs);
        break;
      case registration_numbers_for_cars_with_colour:
        handlePlatesByColor(commandAndArgs);
        break;
      case slot_number_for_registration_number:
        handleSlotByCarPlates(commandAndArgs);
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
    parkingLot = parkingLotFacade.createParkingLot(Integer.valueOf(commandAndArgs[1]));
  }

  private static void handleParkCar(String[] commandAndArgs) throws Exception {
    if (null == getParkingLot()) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = park.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    Car car = new Car(commandAndArgs[1], commandAndArgs[2]);
    parkingLotFacade.parkCar(getParkingLot(), car);
  }

  private static void handleCarLeaves(String[] commandAndArgs) throws Exception {
    if (null == getParkingLot()) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = leave.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.carLeaves(getParkingLot(), Integer.valueOf(commandAndArgs[1]));
  }

  private static void handlePrintStatus(String[] commandAndArgs) throws Exception {
    if (null == getParkingLot()) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = status.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.printStatus(getParkingLot());
  }

  private static void handlePlatesByColor(String[] commandAndArgs) throws Exception {
    if (null == getParkingLot()) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = registration_numbers_for_cars_with_colour.getNumberOfArgs();
    Assert.assertEquals(
            String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.getPlatesByColor(getParkingLot(), commandAndArgs[1]);
  }

  private static void handleSlotByCarPlates(String[] commandAndArgs) throws Exception {
    if (null == getParkingLot()) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = slot_number_for_registration_number.getNumberOfArgs();
    Assert.assertEquals(
            String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.getSlotByPlates(getParkingLot(), commandAndArgs[1]);
  }

  public static ParkingLot getParkingLot() {
    return parkingLot;
  }

  protected static void restart() {
    parkingLot = null;
  }
}

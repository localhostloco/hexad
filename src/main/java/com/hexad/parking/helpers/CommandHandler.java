package com.hexad.parking.helpers;

import com.hexad.parking.facades.ParkingLotFacade;
import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;
import org.junit.Assert;

import static com.hexad.parking.Main.setParkingLot;
import static com.hexad.parking.enums.Commands.*;

public class CommandHandler {
  public static ParkingLotFacade parkingLotFacade;

  private static String invalidNumberOfArgs = "invalid number of arguments (expected: %d)";

  public static void handleCreateParkingLot(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null != parkingLot) throw new Exception("ParkingLot already created!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = create_parking_lot.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    setParkingLot(parkingLotFacade.createParkingLot(Integer.valueOf(commandAndArgs[1])));
  }

  public static void handleParkCar(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = park.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    Car car = new Car(commandAndArgs[1], commandAndArgs[2]);
    parkingLotFacade.parkCar(parkingLot, car);
  }

  public static void handleCarLeaves(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = leave.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.carLeaves(parkingLot, Integer.valueOf(commandAndArgs[1]));
  }

  public static void handlePrintStatus(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = status.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.printStatus(parkingLot);
  }

  public static void handlePlatesByColor(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = registration_numbers_for_cars_with_colour.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.getPlatesByColor(parkingLot, commandAndArgs[1]);
  }

  public static void handleSlotByCarPlates(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = slot_number_for_registration_number.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.getSlotByPlates(parkingLot, commandAndArgs[1]);
  }

  public static void handleSlotsByColor(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception("ParkingLot has not been created yet!");
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = slot_numbers_for_cars_with_colour.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    parkingLotFacade.getSlotsByColor(parkingLot, commandAndArgs[1]);
  }
}

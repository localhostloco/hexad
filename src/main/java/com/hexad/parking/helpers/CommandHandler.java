package com.hexad.parking.helpers;

import com.hexad.parking.facades.ParkingLotFacade;
import com.hexad.parking.facades.ParkingLotFacadeImpl;
import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingLot;
import org.junit.Assert;

import static com.hexad.parking.Main.setParkingLot;
import static com.hexad.parking.enums.Commands.*;

public class CommandHandler {

  private CommandHandler() {}

  private static ParkingLotFacade parkingLotFacade;

  private static final String PARKING_LOT_ALREADY_CREATED = "ParkingLot already created!";
  private static final String invalidNumberOfArgs = "invalid number of arguments (expected: %d)";
  private static final String PARKING_LOT_NOT_CREATED = "ParkingLot has not been created yet!";

  public static void handleCreateParkingLot(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null != parkingLot) throw new Exception(PARKING_LOT_ALREADY_CREATED);
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = create_parking_lot.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    setParkingLot(
        getParkingLotFacadeInstance().createParkingLot(Integer.valueOf(commandAndArgs[1])));
  }

  public static void handleParkCar(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception(PARKING_LOT_NOT_CREATED);
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = park.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    Car car = new Car(commandAndArgs[1], commandAndArgs[2]);
    getParkingLotFacadeInstance().parkCar(parkingLot, car);
  }

  public static void handleCarLeaves(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception(PARKING_LOT_NOT_CREATED);
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = leave.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    getParkingLotFacadeInstance().carLeaves(parkingLot, Integer.valueOf(commandAndArgs[1]));
  }

  public static void handlePrintStatus(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception(PARKING_LOT_NOT_CREATED);
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = status.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    getParkingLotFacadeInstance().printStatus(parkingLot);
  }

  public static void handlePlatesByColor(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception(PARKING_LOT_NOT_CREATED);
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = registration_numbers_for_cars_with_colour.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    getParkingLotFacadeInstance().getPlatesByColor(parkingLot, commandAndArgs[1]);
  }

  public static void handleSlotByCarPlates(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception(PARKING_LOT_NOT_CREATED);
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = slot_number_for_registration_number.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    getParkingLotFacadeInstance().getSlotByPlates(parkingLot, commandAndArgs[1]);
  }

  public static void handleSlotsByColor(ParkingLot parkingLot, String[] commandAndArgs)
      throws Exception {
    if (null == parkingLot) throw new Exception(PARKING_LOT_NOT_CREATED);
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = slot_numbers_for_cars_with_colour.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    getParkingLotFacadeInstance().getSlotsByColor(parkingLot, commandAndArgs[1]);
  }

  public static ParkingLotFacade getParkingLotFacadeInstance() {
    if (null == parkingLotFacade) parkingLotFacade = new ParkingLotFacadeImpl();
    return parkingLotFacade;
  }
}

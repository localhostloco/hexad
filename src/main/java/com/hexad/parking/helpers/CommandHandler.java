package com.hexad.parking.helpers;

import com.hexad.parking.enums.Commands;
import com.hexad.parking.exceptions.ParkingException;
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
      throws ParkingException {
    if (null != parkingLot)
      throw new ParkingException(PARKING_LOT_ALREADY_CREATED, new Throwable());
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = create_parking_lot.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
    setParkingLot(
        getParkingLotFacadeInstance().createParkingLot(Integer.valueOf(commandAndArgs[1])));
  }

  public static void handleParkCar(ParkingLot parkingLot, String[] commandAndArgs)
      throws ParkingException {
    initializeHandlerMethod(parkingLot, PARKING_LOT_NOT_CREATED, commandAndArgs, park);
    Car car = new Car(commandAndArgs[1], commandAndArgs[2]);
    getParkingLotFacadeInstance().parkCar(parkingLot, car);
  }

  public static void handleCarLeaves(ParkingLot parkingLot, String[] commandAndArgs)
      throws ParkingException {
    initializeHandlerMethod(parkingLot, PARKING_LOT_NOT_CREATED, commandAndArgs, leave);
    getParkingLotFacadeInstance().carLeaves(parkingLot, Integer.valueOf(commandAndArgs[1]));
  }

  public static void handlePrintStatus(ParkingLot parkingLot, String[] commandAndArgs)
      throws ParkingException {
    initializeHandlerMethod(parkingLot, PARKING_LOT_NOT_CREATED, commandAndArgs, status);
    getParkingLotFacadeInstance().printStatus(parkingLot);
  }

  public static void handlePlatesByColor(ParkingLot parkingLot, String[] commandAndArgs)
      throws ParkingException {
    initializeHandlerMethod(
        parkingLot,
        PARKING_LOT_NOT_CREATED,
        commandAndArgs,
        registration_numbers_for_cars_with_colour);
    getParkingLotFacadeInstance().getPlatesByColor(parkingLot, commandAndArgs[1]);
  }

  public static void handleSlotByCarPlates(ParkingLot parkingLot, String[] commandAndArgs)
      throws ParkingException {
    initializeHandlerMethod(
        parkingLot, PARKING_LOT_NOT_CREATED, commandAndArgs, slot_number_for_registration_number);
    getParkingLotFacadeInstance().getSlotByPlates(parkingLot, commandAndArgs[1]);
  }

  public static void handleSlotsByColor(ParkingLot parkingLot, String[] commandAndArgs)
      throws ParkingException {
    initializeHandlerMethod(
        parkingLot, PARKING_LOT_NOT_CREATED, commandAndArgs, slot_numbers_for_cars_with_colour);
    getParkingLotFacadeInstance().getSlotsByColor(parkingLot, commandAndArgs[1]);
  }

  private static void initializeHandlerMethod(
      ParkingLot parkingLot, String exceptionMessage, String[] commandAndArgs, Commands command)
      throws ParkingException {
    if (null == parkingLot) throw new ParkingException(exceptionMessage, new Throwable());
    int argsFromInput = commandAndArgs.length - 1;
    int numberOfArgs = command.getNumberOfArgs();
    Assert.assertEquals(
        String.format(invalidNumberOfArgs, numberOfArgs), numberOfArgs, argsFromInput);
  }

  public static ParkingLotFacade getParkingLotFacadeInstance() {
    if (null == parkingLotFacade) parkingLotFacade = new ParkingLotFacadeImpl();
    return parkingLotFacade;
  }
}

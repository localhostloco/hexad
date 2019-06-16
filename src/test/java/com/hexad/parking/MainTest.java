package com.hexad.parking;

import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingSpot;
import com.hexad.parking.facades.ParkingLotFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

  private List<String> args;
  private final String resourcesPath = "src/test/java/resources";
  private String[] argsArray = {};

  private String[] testFiles = {"createParkingLot.in", "parkCar.in", "carLeaves.in", "1.in"};
  private ParkingLotFacade parkingLotFacade;

  @Before
  public void setup() {
    args = new ArrayList<>();
    Main.restart();
  }

  @Test
  public void nullArgumentsThrowAssertionError() {
    assertThrows(AssertionError.class, () -> Main.main(null));
  }

  @Test
  public void canCreateParkingLot() {
    setFileAndRun(testFiles[0]);
    Assert.assertEquals("ParkingLot's spots do not match", Main.getParkingLot().getSize(), 1);
  }

  @Test
  public void canParkCar() {
    setFileAndRun(testFiles[1]);
    Car car = new Car("KA-01-HH-1234", "White");
    ParkingSpot parkingSpot = Main.getParkingLot().getParkingSpotBySlot(0);
    Car parkedCar = parkingSpot.getParkedCar();
    Assert.assertEquals("Car color does not match", parkedCar.getColor(), car.getColor());
    Assert.assertEquals("Car plates do not match", parkedCar.getPlate(), car.getPlate());
  }

  @Test
  public void carCanLeave() {
    setFileAndRun(testFiles[2]);
    ParkingSpot parkingSpot = Main.getParkingLot().getParkingSpotBySlot(0);
    Car parkedCar = parkingSpot.getParkedCar();
    Assert.assertEquals("There shouldn't be a Car parked but there is", parkedCar, null);
  }

  private void setFileAndRun(String filename) {
    args.add(String.format("%s/%s", resourcesPath, filename));
    argsArray = args.toArray(argsArray);
    Main.main(argsArray);
  }
}

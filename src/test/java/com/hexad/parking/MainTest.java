package com.hexad.parking;

import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingSpot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

  private List<String> args;
  private final String resourcesPath = "src/test/java/resources";
  private String[] argsArray = {};

  private String[] testFiles = {
    "createParkingLot.in", "parkCar.in", "carLeaves.in", "checkStatus.in", "1.in"
  };

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
    Assert.assertEquals("ParkingLot's spots do not match", 1, Main.getParkingLot().getSize());
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
    Assert.assertEquals("There shouldn't be a Car parked but there is", null, parkedCar);
  }

  @Test
  public void canCheckStatus() {
    System.setOut(new PrintStream(outContent));
    setFileAndRun(testFiles[3]);
    String status =
        "Created a parking lot with 1 slots\nAllocated slot number: 1\nSlot No.\tRegistration No\tColour\n1\tKA-01-HH-1234\tWhite";
    Assert.assertEquals(status, outContent.toString());
  }

  private void setFileAndRun(String filename) {
    args.add(String.format("%s/%s", resourcesPath, filename));
    argsArray = args.toArray(argsArray);
    Main.main(argsArray);
  }
}

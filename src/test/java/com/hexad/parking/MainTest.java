package com.hexad.parking;

import com.hexad.parking.models.Car;
import com.hexad.parking.models.ParkingSpot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

  private List<String> args;
  private final String resourcesPath = "src/test/java/resources";
  private String[] argsArray = {};

  private String[] inputTestFiles = {
    "createParkingLot.in",
    "parkCar.in",
    "carLeaves.in",
    "checkStatus.in",
    "carPlatesByColor.in",
    "1.in"
  };

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setup() {
    args = new ArrayList<>();
    Main.restart();
  }

  @After
  public void restoreStreams() {
    System.setOut(System.out);
  }

  @Test
  public void nullArgumentsThrowAssertionError() {
    assertThrows(AssertionError.class, () -> Main.main(null));
  }

  @Test
  public void canCreateParkingLot() {
    setFileAndRun(inputTestFiles[0]);
    Assert.assertEquals("ParkingLot's spots do not match", 1, Main.getParkingLot().getSize());
  }

  @Test
  public void canParkCar() {
    setFileAndRun(inputTestFiles[1]);
    Car car = new Car("KA-01-HH-1234", "White");
    ParkingSpot parkingSpot = Main.getParkingLot().getParkingSpotBySlot(0);
    Car parkedCar = parkingSpot.getParkedCar();
    Assert.assertEquals("Car color does not match", parkedCar.getColor(), car.getColor());
    Assert.assertEquals("Car plates do not match", parkedCar.getPlate(), car.getPlate());
  }

  @Test
  public void carCanLeave() {
    setFileAndRun(inputTestFiles[2]);
    ParkingSpot parkingSpot = Main.getParkingLot().getParkingSpotBySlot(0);
    Car parkedCar = parkingSpot.getParkedCar();
    Assert.assertEquals("There shouldn't be a Car parked but there is", null, parkedCar);
  }

  @Test
  public void canCheckStatus() throws IOException {
    System.setOut(new PrintStream(outContent));
    setFileAndRun(inputTestFiles[3]);
    String status = readFromFile("checkStatus.out");
    Assert.assertEquals(status, outContent.toString().replace("\r", ""));
  }

  @Test
  public void canGetCarPlatesByColor() throws IOException {
    System.setOut(new PrintStream(outContent));
    setFileAndRun(inputTestFiles[4]);
    String plates = readFromFile("carPlatesByColor.out");
    Assert.assertEquals(plates, outContent.toString().replace("\r", ""));
  }

  private void setFileAndRun(String filename) {
    args.add(String.format("%s/%s", resourcesPath, filename));
    argsArray = args.toArray(argsArray);
    Main.main(argsArray);
  }

  private String readFromFile(String filename) throws IOException {
    InputStream is = new FileInputStream(String.format("%s/%s", resourcesPath, filename));
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    while (line != null) {
      if (first) {
        sb.append(line);
        first = false;
      } else sb.append("\n").append(line);
      line = buf.readLine();
    }
    buf.close();
    is.close();
    return sb.toString();
  }
}

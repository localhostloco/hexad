package com.hexad.parking;

import com.hexad.parking.exceptions.ParkingException;
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
    "slotByPlates.in",
    "slotsByColor.in",
    "1.in",
    "severalCarCanLeaveFromStart.in",
    "maxOverflow.in",
    "minOverflow.in",
    "complexCase.in"
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
  public void canCreateParkingLot() throws IOException, ParkingException {
    setFileAndRun(inputTestFiles[0]);
    Assert.assertEquals("ParkingLot's spots do not match", 1, Main.getParkingLot().getSize());
  }

  @Test
  public void canParkCar() throws IOException, ParkingException {
    setFileAndRun(inputTestFiles[1]);
    Car car = new Car("KA-01-HH-1234", "White");
    ParkingSpot parkingSpot = Main.getParkingLot().getParkingSpotBySlot(0);
    Car parkedCar = parkingSpot.getParkedCar();
    Assert.assertEquals("Car color does not match", car.getColor(), parkedCar.getColor());
    Assert.assertEquals("Car plates do not match", car.getPlate(), parkedCar.getPlate());
  }

  @Test
  public void carCanLeave() throws IOException, ParkingException {
    setFileAndRun(inputTestFiles[2]);
    ParkingSpot parkingSpot = Main.getParkingLot().getParkingSpotBySlot(0);
    Car parkedCar = parkingSpot.getParkedCar();
    Assert.assertEquals("There shouldn't be a Car parked but there is", null, parkedCar);
  }

  @Test
  public void canCheckStatus() throws IOException, ParkingException {
    prepareOutputToBeIntercepted();
    setFileAndRun(inputTestFiles[3]);
    String status = readFromFile("checkStatus.out");
    Assert.assertEquals(status, outContent.toString().replace("\r", ""));
  }

  @Test
  public void canGetCarPlatesByColor() throws IOException, ParkingException {
    prepareOutputToBeIntercepted();
    setFileAndRun(inputTestFiles[4]);
    String plates = readFromFile("carPlatesByColor.out");
    Assert.assertEquals(plates, outContent.toString().replace("\r", ""));
  }

  @Test
  public void canGetParkingSlotByCarPlates() throws IOException, ParkingException {
    prepareOutputToBeIntercepted();
    setFileAndRun(inputTestFiles[5]);
    String plates = readFromFile("slotByPlates.out");
    Assert.assertEquals(plates, outContent.toString().replace("\r", ""));
  }

  @Test
  public void canGetSlotsByColor() throws IOException, ParkingException {
    prepareOutputToBeIntercepted();
    setFileAndRun(inputTestFiles[6]);
    String plates = readFromFile("slotsByColor.out");
    Assert.assertEquals(plates, outContent.toString().replace("\r", ""));
  }

  @Test
  public void happyPathFromSpecification() throws IOException, ParkingException {
    prepareOutputToBeIntercepted();
    setFileAndRun(inputTestFiles[7]);
    String plates = readFromFile("1.out");
    Assert.assertEquals(plates, outContent.toString().replace("\r", ""));
  }

  @Test
  public void severalCarCanLeaveFromStart() throws IOException, ParkingException {
    final int totalSpots = 6;
    final int totalEmptySpots = 4;
    setFileAndRun(inputTestFiles[8]);
    for (int i = 0; i < totalSpots; i++) {
      ParkingSpot parkingSpot = Main.getParkingLot().getParkingSpotBySlot(i);
      if (i < totalEmptySpots) Assert.assertNull(parkingSpot.getParkedCar());
      else Assert.assertNotNull(parkingSpot.getParkedCar());
    }
  }

  @Test
  public void maxOverflowThrowsException() {
    assertThrows(NumberFormatException.class, () -> setFileAndRun(inputTestFiles[9]));
  }

  @Test
  public void minOverflowThrowsException() {
    assertThrows(NumberFormatException.class, () -> setFileAndRun(inputTestFiles[10]));
  }

  @Test
  public void interactiveModeWorks() throws IOException, ParkingException {
    String specInput =
        "create_parking_lot 6\n"
            + "park KA-01-HH-1234 White\n"
            + "park KA-01-HH-9999 White\n"
            + "park KA-01-BB-0001 Black\n"
            + "park KA-01-HH-7777 Red\n"
            + "park KA-01-HH-2701 Blue\n"
            + "park KA-01-HH-3141 Black\n"
            + "leave 4\n"
            + "status\n"
            + "park KA-01-P-333 White\n"
            + "park DL-12-AA-9999 White\n"
            + "registration_numbers_for_cars_with_colour White\n"
            + "slot_numbers_for_cars_with_colour White\n"
            + "slot_number_for_registration_number KA-01-HH-3141\n"
            + "slot_number_for_registration_number MH-04-AY-1111";
    InputStream manualInput = new ByteArrayInputStream(specInput.getBytes());
    System.setIn(manualInput);
    argsArray = args.toArray(argsArray);
    prepareOutputToBeIntercepted();
    Main.main(argsArray);
    String outputAfterRun = readFromFile("1.out");
    Assert.assertEquals(outputAfterRun, outContent.toString().replace("\r", ""));
  }

  @Test
  public void hugeParkingLotHandlesMemoryAndOperations() throws IOException, ParkingException {
    // takes approximately 19s146ms to run --> 2147483 slots
    prepareOutputToBeIntercepted();
    setFileAndRun(inputTestFiles[11]);
    String output = readFromFile("complexCase.out");
    Assert.assertEquals(output, outContent.toString().replace("\r", ""));
  }

  private void setFileAndRun(String filename) throws IOException, ParkingException {
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

  private void prepareOutputToBeIntercepted() {
    System.setOut(new PrintStream(outContent));
  }
}

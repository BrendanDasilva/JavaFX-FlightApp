package org.example.flightreservationapp;

import java.time.LocalDate;

public class Flight {
  private String flightNumber;
  private String source;
  private String destination;
  private int maxPassengers;
  private LocalDate dateOfTravel;
  private double cost;

  // Constructor
  public Flight(String flightNumber, String source, String destination, int maxPassengers, LocalDate dateOfTravel, double cost) {
    if (!isValidFlightNumber(flightNumber)) {
      throw new IllegalArgumentException("Invalid flight number format.");
    }
    if (!isValidAirportCode(source) || !isValidAirportCode(destination)) {
      throw new IllegalArgumentException("Invalid airport code format.");
    }
    if (dateOfTravel.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Date of travel must be a future date.");
    }
    if (maxPassengers <= 0) {
      throw new IllegalArgumentException("Maximum passengers must be greater than 0.");
    }
    if (cost < 0) {
      throw new IllegalArgumentException("Cost must be non-negative.");
    }

    this.flightNumber = flightNumber;
    this.source = source;
    this.destination = destination;
    this.maxPassengers = maxPassengers;
    this.dateOfTravel = dateOfTravel;
    this.cost = cost;
  }

  // Getters
  public String getFlightNumber() {
    return flightNumber;
  }

  public String getSource() {
    return source;
  }

  public String getDestination() {
    return destination;
  }

  public int getMaxPassengers() {
    return maxPassengers;
  }

  public LocalDate getDateOfTravel() {
    return dateOfTravel;
  }

  public double getCost() {
    return cost;
  }

  // Setters
  public void setFlightNumber(String flightNumber) {
    if (!isValidFlightNumber(flightNumber)) {
      throw new IllegalArgumentException("Invalid flight number format.");
    }
    this.flightNumber = flightNumber;
  }

  public void setSource(String source) {
    if (!isValidAirportCode(source)) {
      throw new IllegalArgumentException("Invalid airport code format.");
    }
    this.source = source;
  }

  public void setDestination(String destination) {
    if (!isValidAirportCode(destination)) {
      throw new IllegalArgumentException("Invalid airport code format.");
    }
    this.destination = destination;
  }

  public void setMaxPassengers(int maxPassengers) {
    if (maxPassengers <= 0) {
      throw new IllegalArgumentException("Maximum passengers must be greater than 0.");
    }
    this.maxPassengers = maxPassengers;
  }

  public void setDateOfTravel(LocalDate dateOfTravel) {
    if (dateOfTravel.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Date of travel must be a future date.");
    }
    this.dateOfTravel = dateOfTravel;
  }

  public void setCost(double cost) {
    if (cost < 0) {
      throw new IllegalArgumentException("Cost must be non-negative.");
    }
    this.cost = cost;
  }

  // Validation methods
  public static boolean isValidFlightNumber(String flightNumber) {
    return flightNumber != null && flightNumber.matches("[a-zA-Z]{2}\\d{4}");
  }

  public static boolean isValidAirportCode(String code) {
    return code != null && code.matches("[a-zA-Z]{3}");
  }

  // Static method to create and validate a Flight object
  public static Flight createFlight(String flightNumber, String source, String destination, int maxPassengers, LocalDate dateOfTravel, double cost) throws IllegalArgumentException {
    // Validate flight number
    if (!isValidFlightNumber(flightNumber)) {
      throw new IllegalArgumentException("Invalid flight number format.");
    }

    // Validate source and destination airport codes
    if (!isValidAirportCode(source)) {
      throw new IllegalArgumentException("Invalid source airport code format.");
    }
    if (!isValidAirportCode(destination)) {
      throw new IllegalArgumentException("Invalid destination airport code format.");
    }

    // Validate max passengers (assuming a range you might have in mind, e.g., 25 to 150)
    if (maxPassengers < 25 || maxPassengers > 150) {
      throw new IllegalArgumentException("Max passengers must be between 25 and 150.");
    }

    // Validate date of travel (it should be a future date)
    if (dateOfTravel.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Date of travel must be a future date.");
    }

    // Validate cost (it should be non-negative)
    if (cost < 0) {
      throw new IllegalArgumentException("Cost must be non-negative.");
    }

    // If all validations pass, create and return the new Flight object
    return new Flight(flightNumber, source, destination, maxPassengers, dateOfTravel, cost);
  }

  // toString implementation
  @Override
  public String toString() {
    return "Flight Details:\n" +
        "---------------------------------\n" +
        "Flight Number: " + flightNumber + "\n" +
        "Source: " + source + "\n" +
        "Destination: " + destination + "\n" +
        "Maximum Passengers: " + maxPassengers + "\n" +
        "Date of Travel: " + dateOfTravel + "\n" +
        "Cost: $" + String.format("%.2f", cost) + "\n" +
        "---------------------------------";
  }

  public String toCSVFormat() {
    return flightNumber + "," + source + "," + destination + "," +
        maxPassengers + "," + dateOfTravel + "," + cost;
  }
}

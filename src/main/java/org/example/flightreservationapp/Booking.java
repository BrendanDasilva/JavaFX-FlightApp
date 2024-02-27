package org.example.flightreservationapp;

import java.util.List;

public class Booking {
  private final Passenger passenger;
  private final Flight flight;

  public Booking(Passenger passenger, Flight flight) {
    this.passenger = passenger;
    this.flight = flight;
  }

  // Getters
  public Passenger getPassenger() {
    return passenger;
  }

  public Flight getFlight() {
    return flight;
  }

  // Static method to create and validate a Booking object
  public static Booking createBooking(Passenger passenger, Flight flight, List<Booking> existingBookings) throws IllegalArgumentException {
    if (passengerAlreadyBooked(passenger, existingBookings)) {
      throw new IllegalArgumentException("This passenger already has a booking.");
    }
    return new Booking(passenger, flight);
  }

  // Helper method to check if the passenger already has a booking
  private static boolean passengerAlreadyBooked(Passenger passenger, List<Booking> existingBookings) {
    return existingBookings.stream().anyMatch(b -> b.getPassenger().equals(passenger));
  }

  // CSV format for saving to file
  public String toCSVFormat() {
    return passenger.getPassportID() + "," + flight.getFlightNumber();
  }

  @Override
  public String toString() {
    return "Booking Details:\n" +
        "---------------------------------\n" +
        "Passenger ID: " + passenger.getPassportID() + "\n" +
        "Full Name: " + passenger.getFirstName() + " " + passenger.getLastName() + "\n" +
        "Flight Number: " + flight.getFlightNumber() + "\n" +
        "Source: " + flight.getSource() + "\n" +
        "Destination: " + flight.getDestination() + "\n" +
        "Cost: $" + flight.getCost() + "\n" +
        "---------------------------------\n";
  }
}

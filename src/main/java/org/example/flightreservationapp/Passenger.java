package org.example.flightreservationapp;

public class Passenger {
  private String passportID;
  private String firstName;
  private String lastName;
  private String age;
  private boolean hasBooked;

  // Constructor
  public Passenger(String passportID, String firstName, String lastName, String age) {
    this.passportID = passportID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public Passenger() {
    // Default constructor
  }

  // Getters
  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAge() {
    return age;
  }

  public boolean hasBooked() {
    return hasBooked;
  }

  public String getPassportID() {
    return passportID;
  }

  // Book a flight (setter for hasBooked)
  public boolean bookFlight() {
    return hasBooked = true;
  }

  // Cancel a flight (setter for hasBooked)
  public boolean cancelFlight() {
    return hasBooked = false;
  }

  // Static method to create and validate a Passenger object
  public static Passenger createPassenger(String passportID, String firstName, String lastName, String age) throws IllegalArgumentException {
    // Validation logic
    if (!passportID.matches("\\d{6}[A-Za-z]{2}")) {
      throw new IllegalArgumentException("Invalid Passport ID format. Must be 6 numbers and 2 letters.");
    }
    if (!firstName.matches("[A-Za-z]{1,25}( [A-Za-z]{1,25}){0,2}")) {
      throw new IllegalArgumentException("Invalid First Name format. Max 25 Characters");
    }
    if (!lastName.matches("[A-Za-z]{1,25}( [A-Za-z]{1,25}){0,2}")) {
      throw new IllegalArgumentException("Invalid Last Name format. Max 25 Characters.");
    }
    int parsedAge;
    try {
      parsedAge = Integer.parseInt(age);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid age format.");
    }
    if (parsedAge <= 0 || parsedAge >= 100) {
      throw new IllegalArgumentException("Age must be between 1 and 99.");
    }

    return new Passenger(passportID, firstName, lastName, age);
  }

  // Method to display passenger info
  public String displayInfo() {
    return "\nFirst Name: " + firstName +
        "\nLast Name: " + lastName +
        "\nAge: " + age +
        "\nPassport ID: " + passportID +
        (hasBooked ? "Has Booked flight" : "Has Not Booked flight");
  }

  @Override
  public String toString() {
    return "Passenger Details:\n" +
        "---------------------------------\n" +
        "Passport ID: " + passportID + "\n" +
        "First Name: " + firstName + "\n" +
        "Last Name: " + lastName + "\n" +
        "Age: " + age + "\n" +
        "Has Booked: " + (hasBooked ? "Yes" : "No") + "\n" +
        "---------------------------------";
  }

  public String toCSVFormat() {
    return passportID + "," + firstName + "," + lastName + "," + age + "," + hasBooked;
  }
}

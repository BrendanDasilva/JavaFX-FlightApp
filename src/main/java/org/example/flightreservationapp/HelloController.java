package org.example.flightreservationapp;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.Node;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class HelloController {
    public BorderPane mainPane;
    private List<Flight> flights = new ArrayList<>();
    private List<Passenger> passengers = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private static final String FLIGHTS_FILE = "flights.txt";
    private static final String PASSENGERS_FILE = "passengers.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";


    // Initialize and add action handlers
    public void initialize() {
        Insets buttonPadding = new Insets(0, 0, 25, 0); // 10px padding at the bottom
        HBox.setMargin(enterButton, buttonPadding);

        enterButton.setOnAction(e -> {
            flightMenuButton.setVisible(true);
            passengerMenuButton.setVisible(true);
            bookingMenuButton.setVisible(true);
            enterButton.setVisible(false);
            flights = loadFlightsFromFile(FLIGHTS_FILE);
            passengers = loadPassengersFromFile(PASSENGERS_FILE);
            bookings = loadBookingsFromFile(BOOKINGS_FILE);
        });

        // Flight Menu
        flightMenuButton.setOnAction(e -> showFlightMenu());

        // Passenger Menu
        passengerMenuButton.setOnAction(e -> showPassengerMenu());

        // Booking Menu
        bookingMenuButton.setOnAction(e -> showBookingMenu());

        // Back Buttons
        backButton.setOnAction(e -> showMainMenu());
        backButton2.setOnAction(e -> showMainMenu());
        backButton3.setOnAction(e -> showMainMenu());
        appExit.setOnAction(e -> System.exit(0)); // Exit the application

    }

    // Main Menu
    private void showMainMenu() {
        mainMenu.setVisible(true);
        flightMenu.setVisible(false);
        passengerMenu.setVisible(false);
        bookingMenu.setVisible(false);
    }

    // Flight Menu
    private void showFlightMenu() {
        mainMenu.setVisible(false);
        flightMenu.setVisible(true); // show flight menu
        passengerMenu.setVisible(false);
    }

    // Passenger Menu
    private void showPassengerMenu() {
        mainMenu.setVisible(false);
        flightMenu.setVisible(false);
        passengerMenu.setVisible(true); // show passenger menu
    }

    // Booking Menu
    private void showBookingMenu() {
        mainMenu.setVisible(false);
        flightMenu.setVisible(false);
        passengerMenu.setVisible(false);
        bookingMenu.setVisible(true); // Show booking menu
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//

    // Start Screen and Main Menu
    @FXML
    private Button enterButton;

    @FXML
    private Button flightMenuButton;

    @FXML
    private Button passengerMenuButton;

    @FXML
    private Button bookingMenuButton;

//    @FXML
//    private Button exitButton;

    @FXML
    private VBox mainMenu;

    @FXML
    private Button appExit;


    // Passenger Menu
    @FXML
    private VBox passengerMenu;

    @FXML
    private Button addPassengerButton;

    @FXML
    private Button deletePassengerButton;

    @FXML
    private Button displayAllPassengersButton;

    @FXML
    private Button backButton;


    // Flight Menu
    @FXML
    private VBox flightMenu;

    @FXML
    private Button addFlightButton;

    @FXML
    private Button deleteFlightButton;

    @FXML
    private Button viewFlightButton;

    @FXML
    private Button displayAllFlightsButton;

    @FXML
    private Button backButton2;


    // Booking Menu
    @FXML
    private VBox bookingMenu;

    @FXML
    private Button addBooking;

    @FXML
    private Button removeBooking;

    @FXML
    private Button viewBookings;

    @FXML
    private Button backButton3;

//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//

    // Flight Menu Functions
    // Adding a flight
    @FXML
    private void handleAddFlight() {
        boolean inputValid;
        do {
            Dialog<Flight> dialog = new Dialog<>();
            dialog.setTitle("Add Flight");

            // Set the button types
            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            // Create GridPane and add input fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField flightNumberField = new TextField();
            flightNumberField.setPromptText("ie. pd1234 or lk5654");

            TextField sourceField = new TextField();
            sourceField.setPromptText("ie. yyz for Toronto");

            TextField destinationField = new TextField();
            destinationField.setPromptText("ie. mdw for Chicago");

            TextField maxPassengersField = new TextField();
            maxPassengersField.setPromptText("Max Passengers 25 - 150");

            DatePicker dateOfTravelPicker = new DatePicker();

            TextField costField = new TextField();
            costField.setPromptText("Cost");

            grid.add(new Label("Flight Number:"), 0, 0);
            grid.add(flightNumberField, 1, 0);
            grid.add(new Label("Source:"), 0, 1);
            grid.add(sourceField, 1, 1);
            grid.add(new Label("Destination:"), 0, 2);
            grid.add(destinationField, 1, 2);
            grid.add(new Label("Max Passengers:"), 0, 3);
            grid.add(maxPassengersField, 1, 3);
            grid.add(new Label("Date of Travel:"), 0, 4);
            grid.add(dateOfTravelPicker, 1, 4);
            grid.add(new Label("Cost:"), 0, 5);
            grid.add(costField, 1, 5);

            dialog.getDialogPane().setContent(grid);

            // Initially disable the Add button
            Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
            addButtonNode.setDisable(true);

            // Listener to check for input in all text fields
            ChangeListener<String> inputChangeListener = (observable, oldValue, newValue) -> {
                addButtonNode.setDisable(
                    flightNumberField.getText().trim().isEmpty() ||
                        sourceField.getText().trim().isEmpty() ||
                        destinationField.getText().trim().isEmpty() ||
                        maxPassengersField.getText().trim().isEmpty() ||
                        dateOfTravelPicker.getValue() == null ||
                        costField.getText().trim().isEmpty()
                );
            };

            flightNumberField.textProperty().addListener(inputChangeListener);
            sourceField.textProperty().addListener(inputChangeListener);
            destinationField.textProperty().addListener(inputChangeListener);
            maxPassengersField.textProperty().addListener(inputChangeListener);
            costField.textProperty().addListener(inputChangeListener);

            // Listener for date picker
            dateOfTravelPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                addButtonNode.setDisable(
                    flightNumberField.getText().trim().isEmpty() ||
                        sourceField.getText().trim().isEmpty() ||
                        destinationField.getText().trim().isEmpty() ||
                        maxPassengersField.getText().trim().isEmpty() ||
                        newValue == null ||
                        costField.getText().trim().isEmpty()
                );
            });

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    try {
                        String flightNumber = flightNumberField.getText();
                        String source = sourceField.getText();
                        String destination = destinationField.getText();
                        int maxPassengers = Integer.parseInt(maxPassengersField.getText());
                        LocalDate dateOfTravel = dateOfTravelPicker.getValue();
                        double cost = Double.parseDouble(costField.getText());

                        // Check for duplicate flight number
                        for (Flight f : flights) {
                            if (f.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                                throw new IllegalArgumentException("Flight number already exists.");
                            }
                        }

                        return Flight.createFlight(flightNumber, source, destination, maxPassengers, dateOfTravel, cost);
                    } catch (IllegalArgumentException e) {
                        showAlert(Alert.AlertType.ERROR, "Input Error", e.getMessage());
                        return null;
                    }
                }
                return null;
            });

            Optional<Flight> result = dialog.showAndWait();
            inputValid = result.isPresent();
            result.ifPresent(flight -> {
                flights.add(flight); // Add the flight to the list
                saveFlightsToFile(flights, FLIGHTS_FILE);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Flight successfully added: " + flight.getFlightNumber());
            });
        } while (!inputValid && userWantsToRetry("adding a flight"));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Viewing a specific flight
    @FXML
    private void handleViewFlight() {
        Dialog<Flight> dialog = new Dialog<>();
        dialog.setTitle("View Flight");

        // Set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the ComboBox for flight selection
        ComboBox<Flight> flightComboBox = new ComboBox<>();
        flightComboBox.setItems(FXCollections.observableArrayList(flights));
        flightComboBox.setConverter(new StringConverter<Flight>() {
            @Override
            public String toString(Flight flight) {
                return flight != null ? flight.getFlightNumber() + " - " + flight.getSource() + " to " + flight.getDestination() : null;
            }

            @Override
            public Flight fromString(String string) {
                return null;
            }
        });

        // Set content of the dialog
        VBox vbox = new VBox(new Label("Select a Flight:"), flightComboBox);
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);

        // Handle the dialog result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return flightComboBox.getValue();
            }
            return null;
        });

        Optional<Flight> result = dialog.showAndWait();
        result.ifPresent(flight -> {
            showAlert(Alert.AlertType.INFORMATION, "Flight Details", flight.toString());
        });
    }

    // Deleting a flight
    @FXML
    private void handleDeleteFlight() {
        Dialog<Flight> dialog = new Dialog<>();
        dialog.setTitle("Delete Flight");

        // Set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the ComboBox for flight selection
        ComboBox<Flight> flightComboBox = new ComboBox<>();
        flightComboBox.setItems(FXCollections.observableArrayList(flights));
        flightComboBox.setConverter(new StringConverter<Flight>() {
            @Override
            public String toString(Flight flight) {
                return flight != null ? flight.getFlightNumber() + " - " + flight.getSource() + " to " + flight.getDestination() : null;
            }

            @Override
            public Flight fromString(String string) {
                return null;
            }
        });

        // Set content of the dialog
        VBox vbox = new VBox(new Label("Select a Flight to Delete:"), flightComboBox);
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);

        // Handle the dialog result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return flightComboBox.getValue();
            }
            return null;
        });

        Optional<Flight> result = dialog.showAndWait();
        result.ifPresent(flight -> {
            // Check if the flight has any bookings
            if (isFlightBooked(flight)) {
                showAlert(Alert.AlertType.ERROR, "Deletion Error", "Cannot delete flight: " + flight.getFlightNumber() + " as it has existing bookings.");
            } else {
                flights.remove(flight); // Remove the flight from the list
                saveFlightsToFile(flights, FLIGHTS_FILE); // Save updated flights to file
                showAlert(Alert.AlertType.INFORMATION, "Success", "Successfully Deleted Flight: " + flight.getFlightNumber());
            }
        });
    }

    private boolean isFlightBooked(Flight flight) {
        return bookings.stream().anyMatch(booking -> booking.getFlight().equals(flight));
    }

    // Viewing all flights
    @FXML
    private void handleViewAllFlights() {
        List<Flight> allFlights = loadFlightsFromFile(FLIGHTS_FILE);

        StringBuilder flightDetails = new StringBuilder();
        if (allFlights.isEmpty()) {
            flightDetails.append("No flights available.");
        } else {
            for (Flight flight : allFlights) {
                flightDetails.append(flight.toString()).append("\n\n");
            }
        }

        showAlert(Alert.AlertType.INFORMATION, "All Flight Details", flightDetails.toString());
    }


    // ---------- File Read and Write ----------
    // Save flights to file
    public void saveFlightsToFile(List<Flight> flights, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Flight flight : flights) {
                writer.write(flight.toCSVFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load flights from file
    public List<Flight> loadFlightsFromFile(String filename) {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Flight flight = new Flight(data[0], data[1], data[2],
                    Integer.parseInt(data[3]),
                    LocalDate.parse(data[4]),
                    Double.parseDouble(data[5]));
                flights.add(flight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flights;
    }


//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//

    // Passenger Menu Functions
    // Add Passenger
    @FXML
    private void handleAddPassenger() {
        boolean inputValid;

        do {
            Dialog<Passenger> dialog = new Dialog<>();
            dialog.setTitle("Add Passenger");

            // Set the button types
            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            // Create GridPane and add input fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField passportIDField = new TextField();
            passportIDField.setPromptText("ie. 123456ab");

            TextField firstNameField = new TextField();
            firstNameField.setPromptText("Max 25 Characters");

            TextField lastNameField = new TextField();
            lastNameField.setPromptText("Max 25 Characters");

            TextField ageField = new TextField();
            ageField.setPromptText("Number between 1 and 99");

            grid.add(new Label("Passport ID:"), 0, 0);
            grid.add(passportIDField, 1, 0);
            grid.add(new Label("First Name:"), 0, 1);
            grid.add(firstNameField, 1, 1);
            grid.add(new Label("Last Name:"), 0, 2);
            grid.add(lastNameField, 1, 2);
            grid.add(new Label("Age:"), 0, 3);
            grid.add(ageField, 1, 3);

            dialog.getDialogPane().setContent(grid);

            // Initially disable the Add button
            Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
            addButtonNode.setDisable(true);

            // Listener to check for input in all text fields
            ChangeListener<String> inputChangeListener = (observable, oldValue, newValue) -> {
                boolean disableButtons = passportIDField.getText().trim().isEmpty() ||
                    firstNameField.getText().trim().isEmpty() ||
                    lastNameField.getText().trim().isEmpty() ||
                    ageField.getText().trim().isEmpty();
                addButtonNode.setDisable(disableButtons);
            };

            // Add the listener to each text field
            passportIDField.textProperty().addListener(inputChangeListener);
            firstNameField.textProperty().addListener(inputChangeListener);
            lastNameField.textProperty().addListener(inputChangeListener);
            ageField.textProperty().addListener(inputChangeListener);

            // Convert the result to a Passenger object when the Add button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    try {
                        // Extract values from text fields
                        String passportID = passportIDField.getText();
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String age = ageField.getText();

                        // Check for duplicate passport ID
                        if (passengers.stream().anyMatch(p -> p.getPassportID().equals(passportID))) {
                            throw new IllegalArgumentException("Passport ID already exists.");
                        }

                        // Use Passenger.createPassenger for creation and validation
                        return Passenger.createPassenger(passportID, firstName, lastName, age);
                    } catch (IllegalArgumentException e) {
                        showAlert(Alert.AlertType.ERROR, "Input Error", e.getMessage());
                        return null;
                    }
                }
                return null;
            });

            Optional<Passenger> result = dialog.showAndWait();

            inputValid = result.isPresent();
            result.ifPresent(passenger -> {
                passengers.add(passenger); // Add the passenger to the list
                savePassengersToFile(passengers, PASSENGERS_FILE); // Save passengers to file
                showAlert(Alert.AlertType.INFORMATION, "Success", "Passenger successfully added: " + passenger.getFirstName());
            });
            if (!inputValid) {
                inputValid = !userWantsToRetry("Add Passenger"); // Ask if the user wants to retry
            }
        } while (!inputValid);
    }

    // View All Passengers
    @FXML
    private void handleViewAllPassengers() {
        List<Passenger> allPassengers = loadPassengersFromFile(PASSENGERS_FILE);

        StringBuilder passengerDetails = new StringBuilder();
        if (allPassengers.isEmpty()) {
            passengerDetails.append("No passengers available.");
        } else {
            for (Passenger passenger : allPassengers) {
                passengerDetails.append(passenger.toString()).append("\n\n");
            }
        }

        showAlert(Alert.AlertType.INFORMATION, "All Passenger Details", passengerDetails.toString());
    }

    // Delete Passenger
    @FXML
    private void handleDeletePassenger() {
        Dialog<Passenger> dialog = new Dialog<>();
        dialog.setTitle("Delete Passenger");

        // Set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the ComboBox for passenger selection
        ComboBox<Passenger> passengerComboBox = new ComboBox<>();
        passengerComboBox.setItems(FXCollections.observableArrayList(passengers));
        passengerComboBox.setConverter(new StringConverter<Passenger>() {
            @Override
            public String toString(Passenger passenger) {
                return passenger != null ? passenger.getPassportID() + " - " + passenger.getFirstName() + " " + passenger.getLastName() : null;
            }

            @Override
            public Passenger fromString(String string) {
                return null;
            }
        });

        // Set content of the dialog
        VBox vbox = new VBox(new Label("Select a Passenger to Delete:"), passengerComboBox);
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);

        // Handle the dialog result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return passengerComboBox.getValue();
            }
            return null;
        });

        Optional<Passenger> result = dialog.showAndWait();
        result.ifPresent(passenger -> {
            if (isPassengerBooked(passenger)) {
                showAlert(Alert.AlertType.ERROR, "Deletion Error", "Cannot delete passenger " + passenger.getFirstName() + " as they have a booking.");
            } else {
                passengers.remove(passenger); // Remove the passenger from the list
                savePassengersToFile(passengers, PASSENGERS_FILE); // Save updated passengers to file
                showAlert(Alert.AlertType.INFORMATION, "Success", "Successfully Deleted Passenger: " + passenger.getFirstName() + " " + passenger.getLastName());
            }
        });
    }

    // Helper method to check if a passenger has a booking
    private boolean isPassengerBooked(Passenger passenger) {
        for (Booking booking : bookings) {
            if (booking.getPassenger().getPassportID().equals(passenger.getPassportID())) {
                return true;
            }
        }
        return false;
    }

    // File I/O methods for passengers
    public void savePassengersToFile(List<Passenger> passengers, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Passenger passenger : passengers) {
                writer.write(passenger.toCSVFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Passenger> loadPassengersFromFile(String filename) {
        List<Passenger> passengers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Passenger passenger = new Passenger(data[0], data[1], data[2], data[3]);
                passengers.add(passenger);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    // Utility methods
    private boolean userWantsToRetry(String action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Retry");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to try " + action + " again?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//
//--------------------------------------------------------------------------------//

    // Booking Menu Functions
    // Add Booking
    @FXML
    private void handleAddBooking() {
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Add Booking");

        // Set the button types
        ButtonType addBookingButton = new ButtonType("Add Booking", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBookingButton, ButtonType.CANCEL);

        // Create GridPane and add dropdown menus
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        ComboBox<Passenger> passengerComboBox = new ComboBox<>(FXCollections.observableArrayList(passengers));
        ComboBox<Flight> flightComboBox = new ComboBox<>(FXCollections.observableArrayList(flights));

        grid.add(new Label("Select Passenger:"), 0, 0);
        grid.add(passengerComboBox, 1, 0);
        grid.add(new Label("Select Flight:"), 0, 1);
        grid.add(flightComboBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Handle dialog result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addBookingButton) {
                Passenger selectedPassenger = passengerComboBox.getValue();
                Flight selectedFlight = flightComboBox.getValue();
                try {
                    // Use Booking.createBooking for creation and validation
                    return Booking.createBooking(selectedPassenger, selectedFlight, bookings);
                } catch (IllegalArgumentException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Booking> result = dialog.showAndWait();
        result.ifPresent(booking -> {
            bookings.add(booking); // Add the new booking to the list
            saveBookingsToFile(bookings, BOOKINGS_FILE); // Save the updated list to file
            showAlert(Alert.AlertType.INFORMATION, "Success", "Booking successfully added: " + booking.toString());
        });
    }

    // Remove Bookings
    @FXML
    private void handleRemoveBooking() {
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Remove Booking");

        // Set the button types
        ButtonType removeBookingButton = new ButtonType("Remove Booking", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeBookingButton, ButtonType.CANCEL);

        ComboBox<Booking> bookingComboBox = new ComboBox<>(FXCollections.observableArrayList(bookings));

        VBox vbox = new VBox(new Label("Select a Booking to Remove:"), bookingComboBox);
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeBookingButton) {
                return bookingComboBox.getValue();
            }
            return null;
        });

        Optional<Booking> result = dialog.showAndWait();
        result.ifPresent(booking -> {
            bookings.remove(booking); // Remove the booking from the list
            saveBookingsToFile(bookings, BOOKINGS_FILE); // Save updated bookings to file
            showAlert(Alert.AlertType.INFORMATION, "Success", "Successfully Removed Booking: " + booking.toString());
        });
    }

    // View Bookings
    @FXML
    private void handleViewBookings() {
        List<Booking> allBookings = loadBookingsFromFile(BOOKINGS_FILE);

        StringBuilder bookingDetails = new StringBuilder();

        if (allBookings.isEmpty()) {
            bookingDetails.append("No bookings available.");
        } else {
            for (Booking booking : allBookings) {
                bookingDetails.append(booking.toString()).append("\n");
            }
        }

        showAlert(Alert.AlertType.INFORMATION, "All Booking Details", bookingDetails.toString());
    }

    // File I/O methods for bookings
    public void saveBookingsToFile(List<Booking> bookings, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Booking booking : bookings) {
                writer.write(booking.toCSVFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> loadBookingsFromFile(String filename) {
        List<Booking> loadedBookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Passenger passenger = findPassengerById(data[0]);
                Flight flight = findFlightByNumber(data[1]);
                if (passenger != null && flight != null) {
                    loadedBookings.add(new Booking(passenger, flight));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Bookings file not found: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedBookings;
    }

    private Passenger findPassengerById(String id) {
        for (Passenger passenger : passengers) {
            if (passenger.getPassportID().equals(id)) {
                return passenger;
            }
        }
        return null;
    }

    private Flight findFlightByNumber(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }


}


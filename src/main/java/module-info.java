module org.example.flightreservationapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.flightreservationapp to javafx.fxml;
    exports org.example.flightreservationapp;
}
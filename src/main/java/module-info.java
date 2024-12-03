module org.example.javaassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javaassignment to javafx.fxml;
    exports org.example.javaassignment;
}
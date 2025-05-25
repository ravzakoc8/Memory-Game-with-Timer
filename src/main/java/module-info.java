module com.ravzakoc.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ravzakoc.project to javafx.fxml;
    exports com.ravzakoc.project;
}
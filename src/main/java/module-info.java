module ru.vorotov.simulationslab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.vorotov.simulationslab3 to javafx.fxml;
    exports ru.vorotov.simulationslab3;
}
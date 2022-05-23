module no.ntnu.wargames{

    requires javafx.fxml;
    requires javafx.controls;

    opens no.ntnu.wargames.frontend.gui.controllers to javafx.fxml, javafx.controls;
    exports no.ntnu.wargames.frontend.gui.controllers;
    opens no.ntnu.wargames.backend.units to javafx.fxml, javafx.controls;
    exports no.ntnu.wargames.backend.units;
    exports no.ntnu.wargames;
    exports no.ntnu.wargames.backend;
    opens no.ntnu.wargames to javafx.fxml, javafx.controls;
    exports no.ntnu.wargames.backend.designPattern;
    opens no.ntnu.wargames.backend.designPattern to javafx.controls, javafx.fxml;
    opens no.ntnu.wargames.backend.file;
    exports no.ntnu.wargames.backend.file;

}
module no.ntnu.wargames{
    requires javafx.controls;
    requires javafx.fxml;

    opens no.ntnu.wargames.frontend.gui.controllers to javafx.fxml;
    exports no.ntnu.wargames.frontend.gui.controllers;
    opens no.ntnu.wargames.backend to javafx.fxml;
    exports no.ntnu.wargames.backend;
    exports no.ntnu.wargames;
    opens no.ntnu.wargames to javafx.fxml;


}
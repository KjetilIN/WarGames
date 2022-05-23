module no.ntnu.wargames{

    requires javafx.fxml;
    requires javafx.controls;

    opens no.ntnu.wargames.frontend.gui.controllers to javafx.fxml, javafx.controls;
    opens no.ntnu.wargames.backend.units to javafx.fxml, javafx.controls;
    opens no.ntnu.wargames to javafx.fxml, javafx.controls;
    opens no.ntnu.wargames.backend.designPattern to javafx.controls, javafx.fxml;
    opens no.ntnu.wargames.backend.file;
    opens no.ntnu.wargames.frontend.gui.dialog.complexDialog to javafx.fxml, javafx.controls;
    opens no.ntnu.wargames.frontend.gui.dialog.simpleDialog to javafx.fxml, javafx.controls;
    opens no.ntnu.wargames.frontend.gui.dialog to javafx.fxml, javafx.controls;

    exports no.ntnu.wargames.frontend.gui.dialog.complexDialog;
    exports no.ntnu.wargames.frontend.gui.dialog.simpleDialog;
    exports no.ntnu.wargames.frontend.gui.dialog;
    exports no.ntnu.wargames.backend;
    exports no.ntnu.wargames.backend.designPattern;
    exports no.ntnu.wargames.backend.file;
    exports no.ntnu.wargames.frontend.gui.controllers;
    exports no.ntnu.wargames.backend.units;
    exports no.ntnu.wargames;

}
package no.ntnu.wargames.frontend.gui.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;

public class AboutDialog extends Dialog<Integer> {

    private String message;

    public AboutDialog(String head, String message){
        super();
        this.message = message;
        getDialogPane().setHeaderText(head);
        createContent();
    }

    private void createContent() {
        //TextArea

        TextArea textArea = new TextArea();
        textArea.setText(this.message);
        textArea.setPrefHeight(300);
        textArea.setMaxHeight(300);

        textArea.setMaxWidth(370);
        textArea.setPrefWidth(370);
        textArea.setEditable(false);

        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(
                        "/no/ntnu/wargames/icon/logoIcon.PNG"))));

        getDialogPane().setContent(textArea);
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

    }

}

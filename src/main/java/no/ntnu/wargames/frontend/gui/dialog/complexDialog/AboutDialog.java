package no.ntnu.wargames.frontend.gui.dialog.complexDialog;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import no.ntnu.wargames.frontend.gui.dialog.WarGamesDialog;

/**
 * Dialog class that has a text area as main node.
 * Extends the abstract WarGamesDialog class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class AboutDialog extends WarGamesDialog<Integer> {

    //Message to be displayed
    private final String message;

    /**
     * Constructor for the AboutDialog.
     *
     * @param head title of the dialog.
     * @param message main information to be displayed in text area.
     */
    public AboutDialog(String head, String message){
        super();
        this.message = message;
        getDialogPane().setHeaderText(head);

        createContent();
    }


    /**
     * Method that creates the content in the pane.
     */
    @Override
    public void createContent() {
        //TextArea

        TextArea textArea = new TextArea();
        textArea.setText(this.message);
        textArea.setPrefHeight(300);
        textArea.setMaxHeight(300);

        textArea.setMaxWidth(370);
        textArea.setPrefWidth(370);
        textArea.setEditable(false);

        updateIcon();

        getDialogPane().setContent(textArea);
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

    }

}

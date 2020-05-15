package gce.module02;

import gce.module02.model.Data;
import gce.module02.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;

public class MainController {
    private List<Item> todoItems;

    @FXML
    private ListView<Item> todoListView;

    @FXML
    private Text itemDetailsText;

    @FXML
    private Label dueDateLabel;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        // Listen to changes in the ListView to display the most recently
        // changed item, whether it is selected programmatically or by the
        // user, or when the user adds a new item to the to-do list
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Item item = todoListView.getSelectionModel().getSelectedItem();
                dueDateLabel.setText("Due on " + item.getItemDueDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
                itemDetailsText.setText(item.getItemDetails());
            }
        });

        // Populate the list view with the to-do items loaded from the text file
        todoListView.getItems().setAll(Data.getInstance().getItems());

        // Ensure that we can only select one item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Select the first to-do item in the list
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void newItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        // Makes the dialog a child of the MainStage (mainBorderPane)
        dialog.initOwner(mainBorderPane.getScene().getWindow());

        // Set the dialog title
        dialog.setTitle("Add new to-do item");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MainDialog.fxml"));

        try {
            // Open the dialog to add a new to-do item
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Error loading add item dialog.");
            e.printStackTrace();
        }

        // Add OK and CANCEL buttons to the DialogPane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // Focus on the new item dialog and ignore other windows of the application
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Get the dialog controller so we can access it directly
            DialogController dialogController = fxmlLoader.getController();

            // Add the data entered into the dialog to the Data model
            Item newItem = dialogController.processResults();

            // Gets items from the Data model to display in the ListView
            todoListView.getItems().setAll(Data.getInstance().getItems());

            // Select the newly added to-do item in the Data model
            todoListView.getSelectionModel().select(newItem);
        } else {
            System.out.println("Cancel pressed");
        }
    }

    /**
     * When an item on the ListView is selected, the item's due date and description
     * are displayed in the TextArea
     */
    @FXML
    public void handleClickListView() {
        Item item = todoListView.getSelectionModel().getSelectedItem();

        dueDateLabel.setText("Due on " + item.getItemDueDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));

        itemDetailsText.setText(item.getItemDetails());
    }
}

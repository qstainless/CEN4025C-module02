package gce.module02.controller;

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
import java.util.Optional;

/**
 * This is the main controller for the application's GUI
 */
public class MainController {

    /**
     * The main stage container
     */
    @FXML
    private BorderPane mainBorderPane;

    /**
     * The to-do item ListView
     */
    @FXML
    private ListView<Item> todoListView;

    /**
     * The to-do items' details are contained in a Text type variable
     */
    @FXML
    private Text itemDetailsText;

    /**
     * The to-do item's due date label
     */
    @FXML
    private Label dueDateLabel;

    /**
     * Presents the dialig to add a to-do item.
     */
    @FXML
    public void newItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        // Makes the dialog a child of the MainStage (mainBorderPane)
        dialog.initOwner(mainBorderPane.getScene().getWindow());

        // Set the dialog title
        dialog.setTitle("Add new to-do item");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/MainDialog.fxml"));

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

            // Select the newly added to-do item in the Data model
            todoListView.getSelectionModel().select(newItem);
        }
    }

    /**
     * Allow the user to delete an item via the To-do menu.
     */
    @FXML
    public void handleMenuDelete() {
        Item selectedItem = todoListView.getSelectionModel().getSelectedItem();

        // We only want to allow the deletion of existing items
        if (selectedItem != null) {
            deleteItem(selectedItem);
        }
    }

    /**
     * Called by the Exit menu item. As opposed to closing the stage, which
     * automatically calls the javafx stop() method, this method will first
     * call the Data singleton to save all to-do items before closing the
     * application.
     */
    @FXML
    public void programExit() {
        Data.getInstance().saveItems();
        System.exit(0);
    }

    /**
     *
     */
    public void initialize() {
        // Populate the ListView with the to-do items in the Data model
        populateListView();
    }

    /**
     * Populates the ListView of the main stage. Listens to changes in the
     * ListView to display the most recently changed item, whether it is
     * selected programmatically or by the user, or when the user adds a new
     * item to the to-do list.
     */
    public void populateListView() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Item item = todoListView.getSelectionModel().getSelectedItem();

                String dueDate = item.getItemDueDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));

                dueDateLabel.setText("Due on " + dueDate);

                itemDetailsText.setText(item.getItemDetails());
            } else {
                /*
                 Remove ghost itemDueDate and itemDetails when the last item
                 is deleted
                */
                dueDateLabel.setText(null);

                itemDetailsText.setText(null);
            }
        });

        // Populate the list view with the to-do items in the Data model
        todoListView.setItems(Data.getInstance().getItems());

        // Ensure that we can only select one item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Select the first to-do item in the list
        todoListView.getSelectionModel().selectFirst();
    }

    /**
     * Deletes the selected to-do item. Will show an alert and ask for
     * confirmation before deleting the item, as the program does not
     * offer an "undo" function.
     *
     * @param item The item in the Data model to be deleted
     */
    public void deleteItem(Item item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(".: WARNING :.");
        alert.setHeaderText("You are about to delete the to-do item:\n\n" +
                item.getItemDescription() + "\n\n" +
                "This action cannot be undone.");
        alert.setContentText("Click OK to confirm deletion.");

        Optional<ButtonType> result = alert.showAndWait();

        // Only delete de to-do item if the user clicks the OK button
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            Data.getInstance().deleteItem(item);
        }
    }
}

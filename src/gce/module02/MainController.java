package gce.module02;

import gce.module02.model.Data;
import gce.module02.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class MainController {
    @FXML
    private ListView<Item> todoListView;

    @FXML
    private Text itemDetailsText;

    @FXML
    private Label dueDateLabel;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ContextMenu listContextMenu;

    public void initialize() {
        // Create a context menu to allow deletion of individual to-do items
        createDeleteContextMenu();

        // Populate the ListView with the to-do items in the Data model
        populateListView();

        // Initialize cell factory for context menu functionality in the ListView
        initializeCellFactory();
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

            // Select the newly added to-do item in the Data model
            todoListView.getSelectionModel().select(newItem);
        }
    }

    /**
     * Initializes the ListView's cell factory method
     */
    public void initializeCellFactory() {
        // We pass an anonymous class to implement the callback interface,
        // which is part of the JavaFX API, and we pass the ListView
        // controller and the return type (the ListCell<>)
        todoListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {

            @Override
            public ListCell<Item> call(ListView<Item> param) {
                ListCell<Item> cell = new ListCell<Item>() {

                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getItemDescription());
                        }
                    }
                };

                // We only want a context menu in non-empty cells
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );

                return cell;
            }
        });
    }

    /**
     * Populates the ListView of the main stage.
     * Listens to changes in the ListView to display the most recently
     * changed item, whether it is selected programmatically or by the
     * user, or when the user adds a new item to the to-do list.
     */
    public void populateListView() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Item item = todoListView.getSelectionModel().getSelectedItem();

                String dueDate = item.getItemDueDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));

                dueDateLabel.setText("Due on " + dueDate);

                itemDetailsText.setText(item.getItemDetails());
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
     * Creates the context menu to delete individual to-do items
     */
    public void createDeleteContextMenu() {
        listContextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete this item");

        deleteItem.setOnAction(event -> {
            Item item = todoListView.getSelectionModel().getSelectedItem();
            deleteItem(item);
        });

        listContextMenu.getItems().addAll(deleteItem);
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

package gce.module02;

import gce.module02.model.Item;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Item> todoItems;

    @FXML
    private ListView<Item> todoListView;

    @FXML
    private Text itemDetailsText;

    @FXML
    private Label dueDateLabel;

    public void initialize() {
        // Listen to changes in the ListView to display the most recently changed item,
        // whether it is selected by the user or when the user adds a new item to the to-do list
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

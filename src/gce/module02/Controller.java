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
        // sample data to test the UI
        Item item1 = new Item("Description 1", "Details 1", LocalDate.now());
        Item item2 = new Item("Description 2", "Details 2", LocalDate.now());
        Item item3 = new Item("Description 3", "Details 3", LocalDate.now());
        Item item4 = new Item("Description 4", "Details 4", LocalDate.now());
        Item item5 = new Item("Description 5", "Details 5", LocalDate.now());

        todoItems = new ArrayList<Item>();
        todoItems.add(item1);
        todoItems.add(item2);
        todoItems.add(item3);
        todoItems.add(item4);
        todoItems.add(item5);

        // Populate the list view with the sample data
        todoListView.getItems().setAll(todoItems);

        // Ensure that we can only select one item at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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

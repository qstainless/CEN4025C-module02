package gce.module02;

import gce.module02.model.Data;
import gce.module02.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField itemDescriptionField;

    @FXML
    private TextArea itemDetailsField;

    @FXML
    private DatePicker itemDueDateField;

    public void processResults() {
        String itemDescription = itemDescriptionField.getText().trim();
        String itemDetails = itemDetailsField.getText().trim();
        LocalDate itemDueDate = itemDueDateField.getValue();

        Data.getInstance().addItem(new Item(itemDescription, itemDetails, itemDueDate));
    }
}

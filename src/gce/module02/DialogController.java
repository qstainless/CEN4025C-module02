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
        // The user may enter tabs in the itemDetails, which will generate
        // an error when loading the items from the text file. To prevent
        // that, we replace all tab characters entered by the user with
        // 4 spaces.
        String itemDetails = itemDetailsField.getText().trim().replace("\t", "    ");
        LocalDate itemDueDate = itemDueDateField.getValue();

        Data.getInstance().addItem(new Item(itemDescription, itemDetails, itemDueDate));
    }
}

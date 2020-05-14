package gce.module02.model;

import java.time.LocalDate;

public class Item {
    private String itemDescription;
    private String itemDetails;
    private LocalDate itemDueDate;

    public Item(String itemDescription, String itemDetails, LocalDate itemDueDate) {
        this.itemDescription = itemDescription;
        this.itemDetails = itemDetails;
        this.itemDueDate = itemDueDate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }

    public LocalDate getItemDueDate() {
        return itemDueDate;
    }

    public void setItemDueDate(LocalDate itemDueDate) {
        this.itemDueDate = itemDueDate;
    }
}

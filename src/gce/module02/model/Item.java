package gce.module02.model;

import java.time.LocalDate;

public class Item {
    private String itemDescription;
    private String itemDetails;
    private LocalDate itemDueDate;

    /**
     * Model constructor
     *
     * @param itemDescription The to-do item description
     * @param itemDetails     The to-do item details
     * @param itemDueDate     The to-do item due date
     */
    public Item(String itemDescription, String itemDetails, LocalDate itemDueDate) {
        this.itemDescription = itemDescription;
        this.itemDetails = itemDetails;
        this.itemDueDate = itemDueDate;
    }

    // Setters and getters
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

    /*
     By default, displaying an item's description displays the object
     reference. We want to display the actual description in the ListView
    */
    @Override
    public String toString() {
        return itemDescription;
    }
}

package gce.module02.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Singleton class to load and save the to-do items to a text file.
 * Saving to a file is not practical for large quantities of data,
 * however, for this assignment's purposes, saving to a text file
 * is convenient.
 */
public class Data {

    private static final Data instance = new Data();

    // Path to the text file that stores the to-do items
    private static final Path path = Paths.get("CastanedaTodoList.txt");

    // Used to format the itemDueDate before saving
    private final DateTimeFormatter formatter;

    // Where the to-do items will be stored in memory
    private ObservableList<Item> items;

    /**
     * Class constructor
     */
    private Data() {
        /*
         Consistent format for saving and loading the to-do item's DueDate
         even though it will be displayed in a different format on the GUI
        */
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    // Getters
    public static Data getInstance() {
        return instance;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    // Add a new to-do item to the Data model
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Loads the to-do items from a text file. If the text files does not
     * already exist in the filesystem, the to-do list will be empty. Once
     * to-do items are added to the Data model, they will be saved to the
     * predefined text file when the user exits the application, either by
     * using the Close option in the To-Do menu or by using the window's
     * close button.
     */
    public void loadItems() {
        // Must use an observableArrayList to populate the GUI ListView
        items = FXCollections.observableArrayList();

        String itemData = readItemsFromFile();

        String[] allItems = itemData.split("_\\.:–=–=–:\\._");

        for (String individualItem : allItems) {
            String[] loadedItems = individualItem.split("\t");

            String itemDescription = loadedItems[0];
            String itemDetails = loadedItems[1];
            String itemDueDate = loadedItems[2];

            LocalDate formattedItemDueDate = LocalDate.parse(itemDueDate, formatter);

            Item item = new Item(itemDescription, itemDetails, formattedItemDueDate);

            items.add(item);
        }
    }

    /**
     * Saves to-do items to a text file when the application is closed.
     * Uses a specific "end-of-item" string to allow for multi-line
     * itemDetails.
     */
    public void saveItems() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (Item item : items) {
                bufferedWriter.write(String.format("%s\t%s\t%s",
                        item.getItemDescription(),
                        item.getItemDetails(),
                        item.getItemDueDate().format(formatter)));
                bufferedWriter.write("_.:–=–=–:._");
            }
        } catch (IOException e) {
            System.out.println("Error writing to-do list items to file.");
        }
    }

    /**
     * Deletes an item from the Data model
     *
     * @param item The item to delete
     */
    public void deleteItem(Item item) {
        items.remove(item);
    }

    /**
     * Reads all to-do items from the text file.
     *
     * @return The string containing all to-do items
     */
    public static String readItemsFromFile() {
        String itemData = "";

        try {
            itemData = new String(Files.readAllBytes(path));
        } catch (IOException ioException) {
            System.out.println("To-do Item text file does not exist. A new file will be created.");
        }

        return itemData;
    }
}

package gce.module02.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
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
    private static final String filename = "CastanedaTodoList.txt";

    private final DateTimeFormatter formatter;

    private ObservableList<Item> items;

    /**
     * Class constructor
     */
    private Data() {
        // Consistent format for saving and loading the to-do item's DueDate
        // even though it will be displayed in a different format on the GUI
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    // Getter and setter
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

        Path path = Paths.get(filename);

        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] loadedItems = input.split("\t");

                String itemDescription = loadedItems[0];
                String itemDetails = loadedItems[1];
                String itemDueDate = loadedItems[2];

                LocalDate formattedItemDueDate = LocalDate.parse(itemDueDate, formatter);

                Item item = new Item(itemDescription, itemDetails, formattedItemDueDate);

                items.add(item);
            }
        } catch (IOException e) {
            System.out.println("Error reading to-do list items from file.");
        }
    }

    /**
     * Saves to-do items to a text file.
     */
    public void saveItems() {
        Path path = Paths.get(filename);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (Item item : items) {
                bufferedWriter.write(String.format("%s\t%s\t%s",
                        item.getItemDescription(),
                        item.getItemDetails(),
                        item.getItemDueDate().format(formatter)));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to-do list items to file.");
        }
    }
}

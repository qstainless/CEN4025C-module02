package gce.module02.model;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

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

    private List<Item> items;

    /**
     * Class constructor
     */
    private Data() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    // Getters and setter
    public static Data getInstance() {
        return instance;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Loads the to-do items from a text file
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
            System.out.println("Error reading to-do list items to file.");
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

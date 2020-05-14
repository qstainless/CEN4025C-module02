package gce.module02;

import gce.module02.model.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Item> todoItems;

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
    }
}

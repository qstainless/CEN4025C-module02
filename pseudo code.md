## To-do application

Guillermo Castaneda Echegaray

CEN 4025C-33718

Dr. Dhrgam AL Kafaf


### Application structure
```
src
    gce
        module02
            model
                Data.java - Singleton class to store the to-do items in memory
                Item.java - Class to store the elements of each to-do item
            view
                MainStage.java - The applications main window layout
                MainDialog.java - The dialog for adding new to-do items
            controller
                MainController.java - Handles the functions of the main stage
                DialogController.java - Handles the functions of the main dialog
            Main.java - The application's point of entry
```

### Main.java
```
public class Main extends JavaFX application

    public static void main
        launch the application

    public void init
        get the singleton instance
        load to-do items from the text file

    public void start
        use FXMLLoader to load the fxml file with the main stage GUI
        set the primary stage title
        set the primary stage scene dimensions
        show the primary stage on the screen

    public void stop
        get the singleton instance
        save the to-do items to the text file
```

### model
#### Item.java
```
public class Item

    Variable decalrations:
        String itemDescription;
        String itemDetails;
        LocalDate itemDueDate;

    public Item(String itemDescription, String itemDetails, LocalDate itemDueDate)
        set passed variable itemDescription to object
        set passed variable itemDetails to object
        set passed variable itemDueDate to object

    public String getItemDescription
        returns the value of itemDescription

    public void setItemDescription(String itemDescription)
        sets the itemDescription value to the object

    public String getItemDetails
        returns the value of itemDetails

    public void setItemDetails(String itemDetails)
        sets the itemDetails value to the object

    public LocalDate getItemDueDate
        returns the value of itemDueDate

    public void setItemDueDate(LocalDate itemDueDate)
        sets the itemDueDate value to the object

    Override the Java toString method
    public String toString()
        returns the String value of itemDescription
```

#### Data.java
```
public class Data {
    Data instance = new Data();

    Variable declarations:
        filename where the to-do items will be saved
        DateTimeFormatter to format itemDueDate
        ObservableList where the to-do items will be stored in memory

    private Data
        Constructor
        declare the date format in which itemDueDate will be saved

    public static Data getInstance
        returns the singleton instance

    public ObservableList<Item> getItems()
        returns the items in the to-do list

    public void addItem(Item item)
        adds the passed item to the Data model

    public void loadItems
        instantiate items as an observable array list in FXCollections

        populate the String itemData by calling the readItemsFromFile method

        split the itemData variable using a unique delimiter and add the delimited values to a temporary array of strings

        parse through the temporary array of strings
            split each element of the array using a tab delimiter (three indexes)
            the first index is assigned to itemDescription
            the second index is assigned to itemDetails
            the third indes is assigned to itemDueDate

            create a new Item instance with the three indexes
            add the created Item instance to the observable array list items

    public static String readItemsFromFile
        tries top read all bytes in the specified file path

        returns the items read from the file (will return null values if it is empty or does not exist)

    public void saveItems
        use BufferedWriter to write all elements in the Data model to the text file
        after writing the elements of each item in the to-do list, add a unique delimiter

    public void deleteItem(Item item)
        use the remove() method to remove the passed item from items
```
 
### view
#### MainStage.fxml
```
    Borderpage divided into four sections
    Top Menu bar
        File Menu
            Exit 
        Edit Menu
            Add new to-do list item
            Delete selected to-do list item

    Left
        Display the ListView of all to-do list items

    Center
        Vertical box
            Horizontal box
                Display the selected item's due date
            Horizontal box
                Display the selected item's details
```

#### MainDialog.fxml
```
    Dialog pane
        add a GridPane with 2 columns 3 rows
            Row to-do item description (title)
            Row to-do item details
            Row to-do item due date DatePicker

        add OK and Cancel buttons
```

### controller
#### MainController.java
```
public class MainController

    FXML annotated object declarations
        private BorderPane mainBorderPane;
        private ListView<Item> todoListView;
        private Text itemDetailsText;
        private Label dueDateLabel;

    FXML annotated methods
        public void newItemDialog
            declare and instantiate the dialog for user input
            assign dialog as a child of the main stage
            set the dialog title
            use FXMLLoader to load the MainDialog.fxml file
            set the dialog content from the fxml file
            add the OK and CANCEL buttons to the dialog
            use Optional showAndWait to focus on the dialog and ignore other windows

            if the OK button is pressed
                get the dialog controller to allow access to its methods
                call the Item processResults method
                select the newly created item on the ListView

        public void handleMenuDelete
            declare the selected item from the todoListView
            if the selectedItem is not null
                delete the selected item by calling the deleteItem method

        public void programExit
            get the singleton instance and save the to-do items to the text file
            exit the program with exit code 0

    Non-annotated methods
        public void initialize
            Call method to populate the ListView with the to-do items in the Data model

        public void populateListView
            listen to events to display to-do list items and select the newly added item

            if a new item is added and its value is not null
                select the new item in the ListView
                format the due date with LONG FormatStyle (e.g., May 15, 2020)
                display the item's due date in the dueDateLabel
                display the item's details in the itemDetailsText
            else
                display the empty item's due date as null (empty)
                display the empty item's details as null (empty)

            Populate the list view with the to-do items in the Data model
            Ensure that we can only select one item at a time
            Select the first to-do item in the list

        public void deleteItem(Item item)
            Create a confirmation alert prior to deletion of the selected item

            if the OK button is pressed
                Call the singleton to delete the selected item from the Data model
```

#### DialogController.java
```
public class DialogController

    FXML annotated object declarations
        private TextField itemDescriptionField;
        private TextArea itemDetailsField;
        private DatePicker itemDueDateField;

    public Item processResults
        gets the values entered by the user in the dialog
            the item's description (title) and trims the whitespace off its content 
            the item's details, trims the whitespace and replaces tab charatcers with 4 spaces
            the item's due date value

        set default values for empty fields
        set tomorrow's date if no due date is selected in the DatePicker

        add the newly created item to the Data model

        return the newly created item

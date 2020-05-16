# To-do List

## Overview
This exercise uses a GUI (Graphical User Interface) that allows a user to create locally stored to-do items.

## What does this program do?
The program allows the user to create (add), view, and delete to-do items. The to-do items are saved to a text file in the local filesystem, which is loaded and parsed every time the application runs.

## System requirements
The program is a JavaFX application using version 8 of Amazon's distribution of the Open Java Development Kit (OpenJDK) [Corretto 8](https://aws.amazon.com/corretto/), which includes JavaFX 8.

## How to use this program.
The GUI consists of a single stage (window) and a single scene (window content). The user can create to-do items by specifying a description of the to-do item, additional details of the item, and a due date. New items can be added by selecting the "Add new item" menu option in the Edit menu or by pressing `CTRL-N`.

The user may also delete a selected item by selecting the "Delete selected item" menu option in the Edit menu or by pressing the `Delete` key.

Newly added to-do items will be saved to a file on the user's filesystem when closing the application window, by selecting the "Exit" menu opion in the File menu, or by pressing `ALT-Q`.

## Installation.
Clone the repo and import it into your favorite Java IDE. Make sure that the project SDK is Java 8 with project language level 8 and JavaFX are installed in your system.

## Known Issues
None at this time

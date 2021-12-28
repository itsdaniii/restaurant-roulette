package ui;

import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Restaurant Tracker App
public class RestaurantApp {
    private static final String JSON_STORE = "./data/restList.json";
    private Restaurant rest;
    private RestaurantList restList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Runs the restaurant application
    // This method has been updated referencing JsonSerializationDemo
    // This method has been updated referencing YouTube tutorial: https://www.youtube.com/watch?v=5o3fMLPY7qY
    public RestaurantApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runApp();
    }

    // MODIFIES: This
    // EFFECTS: Processes user input
    // This method references the TellerApp
    private void runApp() {
        boolean proceed = true;
        String command = null;
        input = new Scanner(System.in);

        initialize();

        while (proceed) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("e")) {
                proceed = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodbye! Avo a good day!");
    }

    // MODIFIES: This
    // EFFECTS: Processes user command
    // This method references the TellerApp
    private void processCommand(String command) {
        if (command.equals("a")) {
            addRestaurant();
        } else if (command.equals("r")) {
            removeRestaurant();
        } else if (command.equals("v")) {
            viewRest();
        } else if (command.equals("p")) {
            printRestList();
        } else if (command.equals("*")) {
            randomizeRest();
        } else if (command.equals("l")) {
            loadRestList();
        } else if (command.equals("s")) {
            saveRestList();
        } else {
            System.out.println("Sorry, invalid selection");
        }
    }

    // EFFECTS: Displays menu of options to user
    // This method references the TellerApp
    private void displayMenu() {
        System.out.println("\nHi! What would you like to do today?:");
        System.out.println("\ta = add a new restaurant");
        System.out.println("\tr = remove a restaurant");
        System.out.println("\tv = view details of a restaurant");
        System.out.println("\tp = print full list of restaurant names");
        System.out.println("\t* = restaurant roulette! (pick me a restaurant)");
        System.out.println("\tl = load restaurant list");
        System.out.println("\ts = save restaurant list");
        System.out.println("\te = exit");
    }

    // MODIFIES: This
    // EFFECTS: Initializes a new RestaurantList with one Restaurant in it
    // This method references the TellerApp
    private void initialize() {
        restList = new RestaurantList();
        rest = new Restaurant("ahn and chi", "vietnamese",
                "mount pleasant", "yes", "$$");
        restList.addRestaurant(rest);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: This
    // EFFECTS: Takes in user input for all parameters relating to the new Restaurant
    //          and performs an add Restaurant function from the list
    private void addRestaurant() {
        String nameInput = selectRestaurant();
        String cuisineInput = inputCuisine();
        String neighbourhoodInput = inputNeighbourhood();
        String patioInput = inputPatio();
        String priceInput = inputPriceRange();
        restList.addRestaurant(new Restaurant(nameInput,
                cuisineInput, neighbourhoodInput, patioInput, priceInput));
    }

    // MODIFIES: This
    // EFFECTS: Takes in user input of restaurant name pertaining to Restaurant they want removed
    //          and performs a remove Restaurant function from the list
    private void removeRestaurant() {
        String nameInput = selectRestaurant();
        restList.removeRestaurant(nameInput);
    }

    // EFFECTS: Compares user input restaurant name and prints all metadata pertaining to that single Restaurant
    //          if there is a match; else, it produces an error message
    private void viewRest() {
        String nameInput = selectRestaurant();
        if (restList.viewRestName(nameInput).equals("No results.")) {
            System.out.println("Sorry, that restaurant doesn't exist yet. Maybe you should add it?");
        } else {
            System.out.println("These are the restaurant details - note them down!");
            System.out.println("Name: " + restList.viewRestName(nameInput));
            System.out.println("Cuisine: " + restList.viewCuisineType(nameInput));
            System.out.println("Neighbourhood: " + restList.viewNeighbourhood(nameInput));
            System.out.println("Has Patio? " + restList.viewHasPatio(nameInput));
            System.out.println("Price Range: " + restList.viewPriceRange(nameInput));
        }
    }

    // EFFECTS: Prints the names of all Restaurants in the List
    private void printRestList() {
        restList.viewRestList();
    }

    // EFFECTS: Prompts user input for restaurant name and returns it
    private String selectRestaurant() {
        String selection = "";
        while ((selection.equals(""))) {
            System.out.println("What is the restaurant name?");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    // EFFECTS: Prompts user input for restaurant cuisine and returns it
    private String inputCuisine() {
        String selection = "";
        while ((selection.equals(""))) {
            System.out.println("What type of cuisine do they serve?");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    // EFFECTS: Prompts user input for restaurant neighbourhood and returns it
    private String inputNeighbourhood() {
        String selection = "";
        while ((selection.equals(""))) {
            System.out.println("What neighbourhood are they located in?");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    // EFFECTS: Prompts user input for whether restaurant has a patio and returns it;
    //          any value other than "yes" or "no" will prevent user from proceeding
    //          until an accepted value is provided
    private String inputPatio() {
        String selection = "";
        while (!(selection.equals("yes") || selection.equals("no"))) {
            System.out.println("Do they have a patio? (yes/no)");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    // EFFECTS: Prompts user input for restaurant price range and returns it;
    //          any value other than "$", "$$", "$$$", "$$$$" will prevent user from proceeding
    //          until an accepted value is provided
    private String inputPriceRange() {
        String selection = "";
        while (!(selection.equals("$") || selection.equals("$$")
                || selection.equals("$$$") || selection.equals("$$$$"))) {
            System.out.println("What is the price range?");
            getPriceRangeDefinition();
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    // EFFECTS: Prints the Price Range Definition
    private void getPriceRangeDefinition() {
        System.out.println("Price Range is defined as");
        System.out.println("$     = < $15 per person");
        System.out.println("$$    = $15 – 30 per person");
        System.out.println("$$$   = $30-50 per person");
        System.out.println("$$$$  = $50+ per person");
    }


    // EFFECTS: saves the restList to file
    // This method references the JsonSerializationDemo
    private void saveRestList() {
        try {
            jsonWriter.open();
            jsonWriter.write(restList);
            jsonWriter.close();
            System.out.println("Saved restaurant list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads restList from file
    // This method references the JsonSerializationDemo
    private void loadRestList() {
        try {
            restList = jsonReader.read();
            System.out.println("Loaded restaurant list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Restaurant Roulette (randomize function)
    // EFFECTS: prints the Restaurant name based on randomly selected Restaurant index
    private void randomizeRest() {
        int randomizeMax = restList.size();
        int r = (int) (Math.random() * randomizeMax);
        System.out.println("Your random restaurant pick is: " + restList.viewRestName(r) + "!");
    }

}

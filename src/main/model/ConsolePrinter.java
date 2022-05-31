package model;

import java.util.List;

public class ConsolePrinter {

    public static void printAddSuccess(Restaurant r) {
        System.out.println("Adding " + r.getRestName() + " to the list.");
    }

    public static void printAddFailed() {
        System.out.println("This restaurant is already in the list. Sounds like you need to eat there ASAP!");
    }

    public static void printRemoveSuccess(String restName) {
        System.out.println("Removing " + restName + " from the list.");
    }

    public static void printRemoveFailed() {
        System.out.println("Sorry, that restaurant doesn't exist yet. Maybe you should add it?");
    }

    public static int printRestList(List<Restaurant> restList) {
        System.out.println("This is your current list of restaurants - better get monching!");
        int printCounter = 0;
        for (Restaurant restaurant : restList) {
            String printRestaurant = restaurant.getRestName();
            System.out.println(printRestaurant);
            printCounter++;
        }
        return printCounter;
    }
}

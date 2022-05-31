package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of Restaurants
public class RestaurantList implements Writeable {
    private List<Restaurant> restList;

    // Creates an empty RestaurantList
    public RestaurantList() {
        restList = new ArrayList<>();
    }

    // EFFECTS: Returns true if restName of Restaurant r is in the restList
    //          and false otherwise
    public boolean contains(Restaurant r) {
        String restName = r.getRestName();
        for (Restaurant restaurant : restList) {
            if (restName.equals(restaurant.getRestName())) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: This
    // EFFECTS: Restaurant r is added to the restList if it's not already in the restList, and
    //          it will print a confirmation message; else, it will produce an error message
    public void addRestaurant(Restaurant r) {
        if (!contains(r)) {
            restList.add(r);
            ConsolePrinter.printAddSuccess(r);
            EventLog.getInstance().logEvent(new Event(r.getRestName()
                    + " has been added to the list."));
        } else {
            ConsolePrinter.printAddFailed();
            EventLog.getInstance().logEvent(new Event("Failed add attempt because " + r.getRestName()
                    + " is a duplicate."));
        }
    }

    // MODIFIES: This
    // EFFECTS: Compares user input restName with restaurant names already in restList
    //          if there is a match, it will remove that Restaurant and print a confirmation message;
    //          else, it will produce an error message
    public void removeRestaurant(String restName) {
        boolean successfulRemoval = false;
        for (int i = 0, restListSize = restList.size(); i < restListSize; i++) {
            Restaurant restaurant = restList.get(i);
            if (restName.equals(restaurant.getRestName())) {
                restList.remove(restaurant);
                successfulRemoval = true;
                break;
            }
        }
        if (!successfulRemoval) {
            ConsolePrinter.printRemoveFailed();
            EventLog.getInstance().logEvent(new Event("Failed removal attempt of " + restName
                    + " because it was not in the list."));
        } else {
            ConsolePrinter.printRemoveSuccess(restName);
            EventLog.getInstance().logEvent(new Event(restName + " has been removed from the list."));
        }
    }

    // This is a console-only function
    // EFFECTS: Prints the restNames of all Restaurants in the restList
    public int viewRestList() {
        return ConsolePrinter.printRestList(restList);
    }

    // Used to return restaurant name at user request
    // EFFECTS: Compares user input restName with restaurants names in restList
    //          if there is a match, it produces the Restaurant name
    //          else, it produces an error message
    public String viewRestName(String restName) {
        for (Restaurant restaurant : restList) {
            if (restName.equals(restaurant.getRestName())) {
                return restaurant.getRestName();
            }
        }
        return "No results.";
    }

    // Used for randomize restaurant function
    // EFFECTS: Returns the restaurant name at specified position i in the list
    public String viewRestName(int randomize) {
        return restList.get(randomize).getRestName();
    }

    // EFFECTS: Compares user input restName with restaurants names in restList
    //          if there is a match, it produces the Restaurant cuisine
    //          else, it produces an error message
    public String viewCuisineType(String restName) {
        for (Restaurant restaurant : restList) {
            if (restName.equals(restaurant.getRestName())) {
                return restaurant.getCuisineType();
            }
        }
        return "No results.";
    }

    // EFFECTS: Compares user input restName with restaurants names in restList
    //          if there is a match, it produces the Restaurant neighbourhood
    //          else, it produces an error message
    public String viewNeighbourhood(String restName) {
        for (Restaurant restaurant : restList) {
            if (restName.equals(restaurant.getRestName())) {
                return restaurant.getNeighbourhood();
            }
        }
        return "No results.";
    }

    // EFFECTS: Compares user input restName with restaurants names in restList
    //          if there is a match, it produces the Restaurant patio result of "yes" or "no"
    //          else, it produces an error message
    public String viewHasPatio(String restName) {
        for (Restaurant restaurant : restList) {
            if (restName.equals(restaurant.getRestName())) {
                return restaurant.getHasPatio();
            }
        }
        return "No results.";
    }

    // EFFECTS: Compares user input restName with restaurants names in restList
    //          if there is a match, it produces the Restaurant price result based on the Price Range Definition
    //          else, it produces an error message;
    // See row 6 of the Restaurant class for Price Range Definition
    public String viewPriceRange(String restName) {
        for (Restaurant restaurant : restList) {
            if (restName.equals(restaurant.getRestName())) {
                return restaurant.getPriceRange();
            }
        }
        return "No results.";
    }

    // EFFECTS: Returns the number of Restaurants in the list
    public int size() {
        return restList.size();
    }

    // Used for persistence
    // EFFECTS: Returns the Restaurant at specified position i in the list
    public Restaurant getRest(int i) {
        return restList.get(i);
    }

    // This method references the JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("restList", restaurantsToRestList());
        return json;
    }

    // EFFECTS: returns restaurants in this restaurant list as a JSON array
    // This method references the JsonSerializationDemo
    private JSONArray restaurantsToRestList() {
        JSONArray jsonArray = new JSONArray();
        for (Restaurant r : restList) {
            jsonArray.put(r.toJson());
            EventLog.getInstance().logEvent(new Event(r.getRestName()
                    + " has been added to the list."));
        }
        return jsonArray;
    }

}

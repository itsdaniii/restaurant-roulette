package model;

import org.json.JSONObject;
import persistence.Writeable;

// Represents a restaurant with the following metadata:
// restaurant name, cuisine type, neighbourhood, patio ("yes"/"no"), and a price range
// "Price Range" is defined as:
//      $     = < $15 per person
//      $$    = $15 – 30 per person
//      $$$   = $30-50 per person
//      $$$$  = $50+ per person
public class Restaurant implements Writeable {
    private String restName;                // restaurant name
    private String cuisineType;             // cuisine type of the restaurant
    private String restNeighbourhood;       // neighbourhood the restaurant is located in
    private String hasPatio;                // "yes" or "no" string result if restaurant has a patio
    private String priceRange;              // indicates the restaurant price range based on definition in row 6


    // REQUIRES: name, cuisine, neighbourhood are non-zero length strings
    //           patio is either "yes" or "no"
    //           priceRange is one of the options as defined in row 6
    //           all inputs are lower case
    // EFFECTS: restName of Restaurant is set to name
    //          cuisineType of Restaurant is set to cuisine
    //          restNeighbourhood of Restaurant is set to neighbourhood
    //          hasPatio of Restaurant is set to patio
    //          priceRange of Restaurant is set to price
    //          restID of Restaurant is a unique ID (positive integer not assigned to another Restaurant)
    public Restaurant(String name, String cuisine, String neighbourhood,
                      String patio, String price) {
        restName = name;
        cuisineType = cuisine;
        restNeighbourhood = neighbourhood;
        hasPatio = patio;
        priceRange = price;
    }

    // getters
    public String getRestName() {
        return restName;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getNeighbourhood() {
        return restNeighbourhood;
    }

    public String getHasPatio() {
        return hasPatio;
    }

    public String getPriceRange() {
        return priceRange;
    }


    // This method references the JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("restName", restName);
        json.put("cuisineType", cuisineType);
        json.put("restNeighbourhood", restNeighbourhood);
        json.put("hasPatio", hasPatio);
        json.put("priceRange", priceRange);
        return json;
    }

    // This method returns the Restaurant name as a string
    // This is used for GUI to ensure restListOptions displays restaurant name, rather than model ID
    @Override
    public String toString() {
        return restName;
    }

}

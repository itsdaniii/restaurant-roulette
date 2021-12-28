package persistence;

import model.Restaurant;
import model.RestaurantList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads restaurant list from JSON data stored in file
// This class references the JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads restaurant list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RestaurantList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRestList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses restaurant list from JSON object and returns it
    private RestaurantList parseRestList(JSONObject jsonObject) {
        RestaurantList restList = new RestaurantList();
        addRestaurantList(restList, jsonObject);
        return restList;
    }

    // MODIFIES: restList
    // EFFECTS: parses restaurants from JSON object and adds them to restaurant list
    private void addRestaurantList(RestaurantList restList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restList");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(restList, nextRestaurant);
        }
    }

    // MODIFIES: restList
    // EFFECTS: parses restaurant from JSON object and adds it to restaurant list
    private void addRestaurant(RestaurantList restList, JSONObject jsonObject) {
        String restName = jsonObject.getString("restName");
        String cuisineType = jsonObject.getString("cuisineType");
        String restNeighbourhood = jsonObject.getString("restNeighbourhood");
        String hasPatio = jsonObject.getString("hasPatio");
        String priceRange = jsonObject.getString("priceRange");
        Restaurant restaurant = new Restaurant(restName, cuisineType,restNeighbourhood,hasPatio,priceRange);
        restList.addRestaurant(restaurant);
    }
}

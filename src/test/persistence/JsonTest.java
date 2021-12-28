package persistence;

import model.Restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references the JsonSerializationDemo
public class JsonTest {
    protected void checkRestaurant(String restName, String cuisineType, String restNeighbourhood,
                                   String hasPatio, String priceRange, Restaurant rest) {
        assertEquals(restName, rest.getRestName());
        assertEquals(cuisineType, rest.getCuisineType());
        assertEquals(hasPatio, rest.getHasPatio());
        assertEquals(restNeighbourhood, rest.getNeighbourhood());
        assertEquals(priceRange, rest.getPriceRange());
    }

}

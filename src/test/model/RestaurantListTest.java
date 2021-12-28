package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

// Unit tests for RestaurantList class
class RestaurantListTest {
    private Restaurant testRestaurant;
    private RestaurantList testRestaurantList;

    @BeforeEach
    void runBefore() {
        testRestaurant = new Restaurant("chipotle", "mexican",
                "downtown", "no", "$");
        testRestaurantList = new RestaurantList();
        testRestaurantList.addRestaurant(testRestaurant);
    }

    @Test
    void testAddRestNew() {
        int currentRestListSize = testRestaurantList.size();
        testRestaurantList.addRestaurant(new Restaurant("ask for luigi", "italian",
                "downtown", "no", "$$$"));
        int updatedRestListSize = testRestaurantList.size();
        assertEquals(updatedRestListSize,currentRestListSize+1);
    }
    @Test
    void testAddRestDuplicate() {
        int currentRestListSize = testRestaurantList.size();
        testRestaurantList.addRestaurant(new Restaurant("chipotle", "mexican",
                "downtown", "no", "$"));
        int updatedRestListSize = testRestaurantList.size();
        assertEquals(updatedRestListSize,currentRestListSize);
    }

    @Test
    void testRemoveRestExists() {
        int currentRestListSize = testRestaurantList.size();
        testRestaurantList.removeRestaurant("chipotle");
        int updatedRestListSize = testRestaurantList.size();
        assertEquals(updatedRestListSize,currentRestListSize-1);
    }

    @Test
    void testRemoveRestDoesntExist() {
        int currentRestListSize = testRestaurantList.size();
        testRestaurantList.removeRestaurant("ask for luigi");
        int updatedRestListSize = testRestaurantList.size();
        assertEquals(updatedRestListSize,currentRestListSize);
    }

    @Test
    void testViewRestNameExists() {
        assertEquals("chipotle",testRestaurantList.viewRestName("chipotle"));
    }

    @Test
    void testViewRestNameDoesntExists() {
        assertEquals("No results.",testRestaurantList.viewRestName("ask for luigi"));
    }

    @Test
    void testViewRestNameRandomize(){
            assertEquals("chipotle",testRestaurantList.viewRestName(0));
    }

    @Test
    void testViewCuisineTypeExists() {
        assertEquals("mexican",testRestaurantList.viewCuisineType("chipotle"));
    }

    @Test
    void testViewCuisineTypeDoesntExists() {
        assertEquals("No results.",testRestaurantList.viewCuisineType("ask for luigi"));
    }

    @Test
    void testViewNeighbourhoodExists() {
        assertEquals("downtown",testRestaurantList.viewNeighbourhood("chipotle"));
    }

    @Test
    void testViewNeighbourhoodDoesntExists() {
        assertEquals("No results.",testRestaurantList.viewNeighbourhood("ask for luigi"));
    }

    @Test
    void testViewHasPatioExists() {
        assertEquals("no",testRestaurantList.viewHasPatio("chipotle"));
    }

    @Test
    void testHasPatioDoesntExists() {
        assertEquals("No results.",testRestaurantList.viewHasPatio("ask for luigi"));
    }

    @Test
    void testViewPriceRangeExists() {
        assertEquals("$",testRestaurantList.viewPriceRange("chipotle"));
    }

    @Test
    void testHasPriceRangeExists() {
        assertEquals("No results.",testRestaurantList.viewPriceRange("ask for luigi"));
    }

    @Test
    void testViewRestList() {
        assertEquals(testRestaurantList.size(),testRestaurantList.viewRestList());
    }

    @Test
    void testGet() {
        assertEquals(testRestaurant,testRestaurantList.getRest(0));
    }
}
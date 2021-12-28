package persistence;

import model.Restaurant;
import model.RestaurantList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// This class references the JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRestList() {
        try {
            RestaurantList restList = new RestaurantList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRestList.json");
            writer.open();
            writer.write(restList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRestList.json");
            restList = reader.read();
            assertEquals(0, restList.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterFilledRestList() {
        try {
            RestaurantList restList = new RestaurantList();
            restList.addRestaurant(new Restaurant("ahn and chi","vietnamese",
                    "mount pleasant","yes","$$"));
            restList.addRestaurant(new Restaurant("chipotle", "mexican",
                    "downtown", "no", "$"));
            JsonWriter writer = new JsonWriter("./data/testWriterFilledRestList.json");
            writer.open();
            writer.write(restList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFilledRestList.json");
            restList = reader.read();
            assertEquals(2, restList.size());
            checkRestaurant("ahn and chi","vietnamese",
                    "mount pleasant","yes","$$",restList.getRest(0));
            checkRestaurant("chipotle", "mexican",
                    "downtown", "no", "$",restList.getRest(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

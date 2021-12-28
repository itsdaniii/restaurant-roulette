package persistence;

import model.RestaurantList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


// This class references the JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RestaurantList restList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRestList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRestList.json");
        try {
            RestaurantList restList  = reader.read();
            assertEquals(0, restList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFilledRestList() {
        JsonReader reader = new JsonReader("./data/testReaderFilledRestList.json");
        try {
            RestaurantList restList  = reader.read();

            assertEquals(2, restList.size());
            checkRestaurant("ahn and chi","vietnamese",
                    "mount pleasant","yes","$$",restList.getRest(0));
            checkRestaurant("chipotle", "mexican",
                    "downtown", "no", "$",restList.getRest(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

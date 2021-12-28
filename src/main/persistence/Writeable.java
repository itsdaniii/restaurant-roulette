package persistence;

import org.json.JSONObject;

// This interface references the JsonSerializationDemo
public interface Writeable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

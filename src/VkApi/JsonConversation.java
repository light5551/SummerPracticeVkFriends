package VkApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class JsonConversation {

    private String error;

    JsonObject parseJson(String str)
    {
        JsonParser jsonParser = new JsonParser();
        return  (JsonObject)jsonParser.parse(str);
    }

    boolean isError(JsonObject obj)
    {
        if (obj.get("error") != null)
        {
             error = "Error code: " + obj.get("error").getAsJsonObject().get("error_code").getAsInt()
                    + "\ndescription: " +
                    obj.get("error").getAsJsonObject().get("error_msg").getAsString();
            return true;
        }
        return false;
    }

    String getErrorMessage()
    {
        return error;
    }

    JsonArray getItemsFromResponse(JsonObject obj)
    {
        return obj.get("response").getAsJsonObject().getAsJsonArray("items");
    }

    JsonObject getJsonObject(JsonObject obj)
    {
        return obj.get("response").getAsJsonArray().get(0).getAsJsonObject();
    }

    int getObjectId(JsonObject obj)
    {
        return obj.get("response").getAsJsonObject().get("object_id").getAsInt();
    }
}

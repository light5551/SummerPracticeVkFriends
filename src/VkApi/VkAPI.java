package VkApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;

public class VkAPI {

    private final String accessVkApiToken = "0c91fdfc0c91fdfc0c91fdfc3e0cfa8e5100c910c91fdfc518a61b257703bf5b0589264";
    private final String versionVkApi = "5.101";
    private final String beginVkApi = "https://api.vk.com/method/";
    private final String endVkApi = "&access_token=" + accessVkApiToken + "&v=" + versionVkApi;

    public ArrayList<VKUser> parseFriendsJson(String str) {
        try
        {
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject)jsonParser.parse(str);
            if (jo.get("error") != null)
            {
                System.out.println("Error code: " + jo.get("error").getAsJsonObject().get("error_code").getAsInt()
                                   + "\ndescription: " +
                                   jo.get("error").getAsJsonObject().get("error_msg").getAsString());
                return null;
            }


            JsonArray jsonArr = jo.get("response").getAsJsonObject().getAsJsonArray("items");
            ArrayList<VKUser> list = new ArrayList<>();

            for (JsonElement obj : jsonArr)
            {
                JsonObject tmp = obj.getAsJsonObject();
                list.add( new VKUser( tmp.get("id").getAsInt(),
                        tmp.get("first_name").getAsString(),
                        tmp.get("last_name").getAsString()));
            }

            return list;
        }
        catch (Exception e)
        {
            System.out.println("Failed: parseFriendsJson");
            e.printStackTrace();
        }
        return null;
    }

    public String getUserFriends(int userId, String[] args)
    {
        /*
        StringBuilder sb = new StringBuilder();
        sb.append(beginVkApi + "friends.get?user_id=" + userId);
        if(args != null) {
            sb.append("&fields=");
            for (String field : args)
                sb.append(field + ",");
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(endVkApi);
        */
        String sb = createGetRequest("friends.get?user_id=", userId, args);

        return getRequest(sb.toString());
    }

    public VKUser getUser(int userId, String[] args)
    {
        String sb = createGetRequest("users.get?user_ids=", userId, args);
        String request = getRequest(sb);

        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(request);

        JsonElement response = jo.get("response").getAsJsonArray().get(0).getAsJsonObject().get("id");
        System.out.println(response.toString());


        return new VKUser(1, "aa", "bb");
        //return getRequest(sb.toString());
    }

    private String createGetRequest(String method, int id, String[] args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(beginVkApi + method + id);
        if(args != null) {
            sb.append("&fields=");
            for (String field : args)
                sb.append(field + ",");
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(endVkApi);
        return sb.toString();
    }
    private String getRequest(String url)
    {
        StringBuilder sb = new StringBuilder();
        try {
            URL requestUrl=new URL(url);
            URLConnection con = requestUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            int cp;
            while ((cp = in.read()) != -1) {
                sb.append((char) cp);
            }
        }
        catch(Exception e) {
            System.out.println("Failed: getRequest");
        }
        return sb.toString();
    }

}

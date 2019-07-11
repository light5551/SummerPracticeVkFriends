package VkApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;


public class VkAPI {

    static int currentUserId;
    static VKUser currentVkUser;
    private final String accessVkApiToken = "<key of app>";
    private final String versionVkApi = "5.101";
    private final String beginVkApi = "https://api.vk.com/method/";
    private final String endVkApi = "&access_token=" + accessVkApiToken + "&v=" + versionVkApi;
    private final int lengthVkDomen = 15;
    private JsonConversation conversationJson;

    public VkAPI(){
        conversationJson = new JsonConversation();
        currentUserId = 1;
    }

    public static void updateCurrentUser(int id)
    {
        currentUserId = id;
        VkAPI vk = new VkAPI();
        currentVkUser = vk.getUser(id, null);
    }

    public static VKUser getCurrentUser()
    {
        return currentVkUser;
    }

    public ArrayList<VKUser> parseFriendsJson(String str) {
        try
        {
            JsonObject jo = conversationJson.parseJson(str);
            if (conversationJson.isError(jo))
            {
                System.out.println(conversationJson.getErrorMessage());
                return null;
            }

            JsonArray jsonArr = conversationJson.getItemsFromResponse(jo);
            ArrayList<VKUser> list = new ArrayList<>();

            for (JsonElement obj : jsonArr)
            {
                JsonObject tmp = obj.getAsJsonObject();
                list.add( new VKUser( tmp.get("id").getAsInt(),
                        tmp.get("first_name").getAsString(),
                        tmp.get("last_name").getAsString(),
                        tmp.get("photo_50").getAsString()));
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

    public String getUserFriends(int userId,  String order, String[] args)
    {
        return getRequest(createGetRequest("friends.get?user_id=", userId, order, args));
    }

    public VKUser getUser(int userId, String[] args)
    {
        if (args == null)
        {
            args = new String[]{"photo_50", "bdate", "games", "domain", "sex", "online", "music"};
        }

        String request = getRequest(createGetRequest("users.get?user_ids=", userId,null, args));
        JsonObject jo = conversationJson.parseJson(request);

        if (conversationJson.isError(jo))
        {
            System.out.println(conversationJson.getErrorMessage());
            return null;
        }

        JsonObject response = conversationJson.getJsonObject(jo);
        System.out.println(request);
        System.out.println(jo.toString());
        String bdate = "";
        try {
            bdate = response.get("bdate").getAsString();
        }
        catch (Exception E)
        {
            bdate = "NO DATA";
        }
        return new VKUser(response.get("id").getAsInt(),
                          response.get("first_name").getAsString(),
                          response.get("last_name").getAsString(),
                          response.get("photo_50").getAsString(),
                          response.get("sex").getAsInt(),
                          response.get("domain").getAsString(),
                          bdate,
                          response.get("online").getAsInt());//,
                          //response.get("music").getAsString(),
                          //response.get("games").getAsString());
    }

    private String createGetRequest(String method, int id, String order, String[] args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(beginVkApi + method + id);
        if (order != null)
            sb.append("&order=" + order);
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

    public ArrayList<VKUser> getFriends(int userId,  String order, String[] args)
    {
        String response = getUserFriends(userId, order, args);
        return parseFriendsJson(response);
    }

    public int getIdByUrl(String url)
    {
        String account = getAccountNameFromUrl(url);
        if (account != null) {
            var request = getRequest(convertVkUrlToVkApiUrl(account));
            return conversationJson.getObjectId(conversationJson.parseJson(request));
        }
        return 0;
    }

    private String getAccountNameFromUrl(String url)
    {
        var newStr = url.substring(lengthVkDomen);
        if (newStr.contains("/")||newStr.contains("?")||newStr.contains("&"))
            return null;
        return newStr;
    }

    private String convertVkUrlToVkApiUrl(String accountName)
    {
        return beginVkApi + "utils.resolveScreenName?screen_name=" + accountName + endVkApi;
    }
  
    public ArrayList<VKUser> getCommonFriends(int firstId, int secondId)
    {
        var first = getFriends(firstId, "name", new String[]{"photo_50"});
        var second = getFriends(secondId,"name", new String[]{"photo_50"});
        ArrayList<VKUser> commonFriends = new ArrayList<>();
        for (var i : second) {
            if (first.contains(i)) {
                commonFriends.add(i);
            }
       }
       return commonFriends;

    }
}

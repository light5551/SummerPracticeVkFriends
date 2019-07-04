package Main;

import VkApi.VkAPI;
import VkApi.VKUser;

import java.util.ArrayList;

public class Main {

    private static final String[]  requestArgs = {"photo50", "education"};

    public static void main(String[] args) {

        VkAPI vk = new VkAPI();
        // id of user
        int userID = 1411555542;
        // get json
        String response = vk.getUserFriends(userID, requestArgs);
        ArrayList<VKUser> list;
        list = vk.parseFriendsJson(response);// json -> ArrayList

        // this only for result
        if (list != null)
            for (VKUser el : list)
                System.out.println(el.toString());
    }
}
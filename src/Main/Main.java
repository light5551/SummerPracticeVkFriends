package Main;

import VkApi.VkAPI;
import VkApi.VKUser;

import java.util.ArrayList;

public class Main {

    private static final String[]  requestArgs = {"photo50", "education"};
    private static final String orderFriends = "name";
    public static void main(String[] args) {

        VkAPI vk = new VkAPI();
        // id of user
        int userID = 179878269;
        // get json

        String response = vk.getUserFriends(userID, orderFriends, requestArgs);
        ArrayList<VKUser> list;

        //информация о человеке
        System.out.println(vk.getUser(userID, null));

        list = vk.parseFriendsJson(response);// json -> ArrayList
        // this only for result
        if (list != null)
            for (VKUser el : list)
                System.out.println(el.toString());
    }
}

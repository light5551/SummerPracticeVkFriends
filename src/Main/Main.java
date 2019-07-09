package Main;

import VkApi.VkAPI;
import VkApi.VKUser;

import Visualizator.*;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    private static final String[]  requestArgs = {"photo_50", "education"};
    private static final String orderFriends = "name";
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Visualizator.initGUI();
    }

    public static ArrayList<VKUser> getList() {
        VkAPI vk = new VkAPI();
        // id of user

        int userID = 179878269;
        vk.updateCurrentUser(userID);

        // get json

        String response = vk.getUserFriends(userID, orderFriends, requestArgs);
        ArrayList<VKUser> list;

        //информация о человеке
        System.out.println(vk.getUser(userID, null));

        VKUser curUser = vk.getCurrentUser();
        System.out.println(curUser.toString());

        list = vk.parseFriendsJson(response);// json -> ArrayList

        // or you can do it in one click
        // list = vk.getFriends(userID, orderFriends, requestArgs);


        // this only for result
        if (list != null)
            if (!list.isEmpty())
                for (VKUser el : list)
                    System.out.println(el.toString());
            else
                System.out.println("Empty list");
        return list;
    }
}

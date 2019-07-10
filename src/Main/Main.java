package Main;

import VkApi.VkAPI;
import VkApi.VKUser;

import Visualizator.*;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    private static final String[] requestArgs = {"photo_50", "education"};
    private static final String orderFriends = "name";
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Visualizator.initGUI();
    }

    public static ArrayList<VKUser> getList() {
        VkAPI vk = new VkAPI();

        int userID = 179878269;
        VkAPI.updateCurrentUser(userID);
        ArrayList<VKUser> list = vk.getFriends(VkAPI.getCurrentUser().userId, orderFriends, requestArgs);
        
        int id = vk.getIdByUrl("https://vk.com/consolewritesj");
        System.out.println(id);

        var friendsAnnaSergey = vk.getCommonFriends(VkAPI.getCurrentUser().userId, 141845542);
        for (var i : friendsAnnaSergey)
            System.out.println(i.toString());

        return list;
    }
}

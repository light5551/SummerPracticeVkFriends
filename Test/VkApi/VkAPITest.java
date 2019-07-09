package VkApi;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class VkAPITest extends Assert{


    private static final String[]  requestArgs = {"photo50", "education"};
    private static final String orderFriends = "name";
    VkAPI test = new VkAPI();
    int userID;

    @Test
    public void parseFriendsJson() {

        VkAPI.updateCurrentUser(userID);
        VkAPI.getCurrentUser();
        String response = test.getUserFriends(userID, orderFriends, requestArgs);
        ArrayList<VKUser> expected =  test.parseFriendsJson(response);
        File file = new File(new File("").getAbsolutePath()+"\\Test\\VkApi\\test1.txt");
        String path = file.getAbsolutePath();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String actual;
            int i=0;
            while((actual=br.readLine())!=null){
                Assert.assertNotNull(actual);
                System.out.println("actual is not null");
                System.out.println(actual);
                Assert.assertNotNull(expected.get(i).toString());
                System.out.println("expected is not null");
                System.out.println(expected);
                Assert.assertEquals(expected.get(i).toString(),actual);
                System.out.println(i+1 + ". " + expected.get(i).toString() + " " + actual + " EQUALS");
                i++;
            }
        }
        catch (Throwable e){
            System.out.println("Error"+e);
        }

    }

    @Test
    public void getUser(){

        VkAPI.updateCurrentUser(userID);
        VkAPI.getCurrentUser();
        VKUser expectedUser =  test.getUser(userID, requestArgs);
        Assert.assertNotNull(expectedUser);
        System.out.println("Valid");
        System.out.println(expectedUser);
    }
}
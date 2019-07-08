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
    int userID = 179878269;
    VkAPI test = new VkAPI();

    @Test
    public void parseFriendsJson() {
        String response = test.getUserFriends(userID, orderFriends, requestArgs);
        ArrayList<VKUser> expected =  test.parseFriendsJson(response);
        File file = new File(new File("").getAbsolutePath()+"\\Test\\VkApi\\test1.txt");
        String path = file.getAbsolutePath();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String actual;
            int i=0;
            while((actual=br.readLine())!=null){
                Assert.assertEquals(expected.get(i).toString().replace(" ", ""),actual.replace(" ", ""));
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
        VKUser user = new VKUser(179878269,"Sergey", "Glazunov");
        VKUser expectedUser =  test.getUser(userID, requestArgs);
        Assert.assertEquals(user, expectedUser);
        System.out.println("Valid");
    }
}
package VkApi;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class VkAPITest extends Assert{


    private static final String[]  requestArgs = {"photo_50"};
    private static final String orderFriends = "name";
    VkAPI test = new VkAPI();

    @Test //тест для начального этапа разработки
    public void parseFriendsJson() {
        int userID = 179878269;
        String response = test.getUserFriends(userID, orderFriends, requestArgs);
        ArrayList<VKUser> expected =  test.parseFriendsJson(response);
        String path = System.getProperty("user.dir")+("\\Test\\VkApi\\test1.txt");
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
        String path = System.getProperty("user.dir")+("\\Test\\VkApi\\test2.txt");
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String actual;
            int i=0;
            while((actual=br.readLine())!=null){
                int userID = Integer.parseInt(actual);
                //test.getUser(userID,requestArgs);
                Assert.assertNotNull(test.getUser(userID,requestArgs));
                System.out.println(test.getUser(userID,requestArgs));
                i++;
            }
        }
        catch (Throwable e){
            System.out.println("Error"+e);
        }
    }

    @Test
    public void getCommonFriends(){
        String path = System.getProperty("user.dir")+("\\Test\\VkApi\\test3.txt");
        int ID = 206043986;
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String actual;
            int i=0;
            while((actual=br.readLine())!=null){
                int userID = Integer.parseInt(actual);
                test.getCommonFriends(userID,ID);
                //Assert.assertNotNull(test.getUser(userID,requestArgs));
                System.out.println(test.getCommonFriends(userID,ID));
                i++;
            }
        }
        catch (Throwable e){
            System.out.println("Error"+e);
        }
    }
}
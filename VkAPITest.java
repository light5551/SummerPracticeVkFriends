package VkApi;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class VkAPITest extends Assert {

    private static final String[]  requestArgs = {"photo50", "education"};
    private static final String orderFriends = "name";
    int userID = 177754919;
    VkAPI test = new VkAPI();

    @Test
    public void parseFriendsJson() {
        String response = test.getUserFriends(userID, orderFriends, requestArgs);
        VKUser user1 = new VKUser(255335617,"Dana", "Murtazina");
        VKUser user2 = new VKUser(86251509,"Malika", "Isengeldinova");
        ArrayList<VKUser> expected =  test.parseFriendsJson(response);
        ArrayList<VKUser> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);

        Assert.assertEquals(actual, expected);
        Assert.assertNotNull(expected);

    }

    @Test
    public void getUser(){
        VKUser user = new VKUser(177754919,"Dana", "Murtazina");
        VKUser expectedUser =  test.getUser(userID, requestArgs);
        Assert.assertEquals(user, expectedUser);
    }
}

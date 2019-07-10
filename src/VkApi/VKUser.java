package VkApi;

public class VKUser {
    public int userId;
    public String firstName;
    public String lastName;
    public String urlImage_50;

    public VKUser(
            int userId,
            String firstName,
            String lastName,
            String urlImage_50)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.urlImage_50 = urlImage_50;
    }

    @Override
    public String toString() {
        return userId + " " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        VKUser tmp = (VKUser)obj;
        if (tmp.userId == this.userId)
            return true;
        return false;
    }
}

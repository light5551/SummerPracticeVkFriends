package VkApi;

public class VKUser {
    public int userId;
    public String firstName;
    public String lastName;
    /*public boolean is_closed;
    public boolean can_access_closed;
    public int sex;
    public String bdate;
    public String university_name;
    public String faculty_name;
    public Place city;
    public Place country; */

    public VKUser(
            int userId,
            String firstName,
            String lastName)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return userId + " " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        VKUser tmp = (VKUser)obj;
        if (this.firstName.equals(tmp.firstName))
            if (this.lastName.equals(tmp.lastName))
                return tmp.userId == this.userId;
        if (tmp.userId == this.userId)
            return true;
        return false;
    }
}

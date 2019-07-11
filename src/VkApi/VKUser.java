package VkApi;

public class VKUser {
    public int userId;
    public String firstName;
    public String lastName;
    public String urlImage_50;
    int sex;
    public String domain;
    public String bdate;
    public int online;
    public String music;
    public String games;

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


    public VKUser(
            int userId,
            String firstName,
            String lastName,
            String urlImage_50,
            int sex,
            String domain,
            String bdate,
            int online)//,
          //  String music,
          //  String games)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.urlImage_50 = urlImage_50;
        this.sex = sex;
        this.domain = domain;
        this.bdate = bdate;
        this.online = online;
       // this.music = music;
       // this.games = games;
    }

    @Override
    public String toString() {
        String _sex = "woman";
        Boolean onl = true;
        if (this.online == 0)
            onl = false;
        if (this.sex == 2)
            _sex = "man";
        return "Id: " + userId + "\n" +
                "Name: " + firstName + "\n" +
                "Surname: " + lastName + "\n" +
                "Sex: " + _sex + "\n"+
                "Domain: " + domain + "\n"+
                "Date of birthday: " + bdate + "\n"+
                "Online: " + onl + "\n";//+
               // "Music: " + music + "\n"+
                //"Games: " + games + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        VKUser tmp = (VKUser)obj;
        if (tmp.userId == this.userId)
            return true;
        return false;
    }
}

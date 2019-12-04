package sample;

import java.util.*;


// A FacebookUser on social network has id, friends and other FB_User_Info
class FacebookUser
{
    private ArrayList<Integer> friends =
            new ArrayList<Integer>();
    private int FB_User_ID;
    private String FB_User_Info;
    private String name;
    private String city, state;

    public FacebookUser(int id, String name, String city, String state)
    {
        this.FB_User_ID =id;
        this.name =name;
        this.city =city;
        this.state =state;
    }
    public void setinfo(String FB_User_Info)
    {
        this.FB_User_Info = FB_User_Info;
    }

    public ArrayList<Integer> getFriends()
    {
        return friends;
    }
    public int getID()
    {
        return FB_User_ID;
    }

    public String getLocation(){
        return city + ", " + state;
    }
    public String getName(){
        return name;
    }


    public void addFriend(int id)
    {
        friends.add(id);
    }
    public String getinfo()
    {
        return FB_User_Info;
    }

}
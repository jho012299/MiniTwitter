package cs3560;

import java.util.List;

public class User implements TreeEntry{
    private String id;
    private List<String> followers;
    private List<String> following;
    private List<String> feed;

    public User(String id) {
        this.id = id;
    }

    public void follow(String id) {
        following.add(id);
    }

    public String post(String message) {
        return "";
    }

    public String getId() {
        return id;
    }

    //update following view + update feed
}

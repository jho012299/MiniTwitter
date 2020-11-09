package cs3560;

import java.util.ArrayList;
import java.util.List;

public class User implements TreeEntry{
    private String id;
    private List<String> followers;
    private List<String> following;
    private List<String> feed;

    public User(String id) {
        this.id = id;
        followers = new ArrayList<>();
        following = new ArrayList<>();
        feed = new ArrayList<>();
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

    @Override
    public String toString() {
        return id;
    }

    //update following view + update feed
}

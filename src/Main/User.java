package Main;

import Observer.Observer;
import Observer.Subject;
import Visitor.StatsElementVisitor;

import java.util.ArrayList;
import java.util.List;

public class User extends Subject implements TreeEntry, Observer {
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
        // attach
        following.add(id);
    }

    public String post(String message) {
        notifyObservers();
        return "";
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    public void accept(StatsElementVisitor visitor) {
        visitor.visitUser(this);
    }

    public void update(Subject subject) {

    }
}

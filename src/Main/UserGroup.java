package Main;

import Visitor.StatsElementVisitor;

import java.util.ArrayList;
import java.util.List;

public class UserGroup implements TreeEntry{
    private String id;
    private List<TreeEntry> entries; // UserGroup can store Users and UserGroups

    public UserGroup(String id) {
        this.id = id;
        entries = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<TreeEntry> getList() {
        return entries;
    }

    public void addUser(String id) {
        entries.add(new User(id));
    }

    public void addUserGroup(String id) {
        entries.add(new UserGroup(id));
    }

    public void accept(StatsElementVisitor visitor) { // recursive method to visit each tree entry
        for (TreeEntry t : entries) {
            t.accept(visitor);
        }
        visitor.visit(this);
    }
}

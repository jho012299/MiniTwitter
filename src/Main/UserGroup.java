package Main;

import Visitor.StatsElementVisitor;

import java.util.ArrayList;
import java.util.List;

public class UserGroup implements TreeEntry{
    private String id;
    private List<TreeEntry> entries;

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

    public int accept(StatsElementVisitor visitor) {
        return visitor.visit(this);
    }
}

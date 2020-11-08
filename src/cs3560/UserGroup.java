package cs3560;

import java.util.List;

public class UserGroup implements TreeEntry{
    private String id;
    private List<TreeEntry> entries;

    public UserGroup(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addUser(String id) {
        entries.add(new User(id));
    }

    public void addUserGroup(String id) {
        entries.add(new UserGroup(id));
    }

}

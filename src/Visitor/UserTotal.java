package Visitor;

import Main.TreeEntry;
import Main.User;
import Main.UserGroup;

import java.util.List;

public class UserTotal implements StatsElementVisitor {
    private int count;

    public UserTotal() {
        count = 0;
    }

    @Override
    public void visitUser(User user) {
        count++;
    }

    @Override
    public void visitGroup(UserGroup userGroup) {
        List<TreeEntry> list = userGroup.getList();
        for (TreeEntry t : list) {
            if (t instanceof User) {
                visitUser((User) t);
            }
            else if (t instanceof UserGroup) {
                t.accept(this);
            }
        }
    }

    public int getCount() {
        return count;
    }
}

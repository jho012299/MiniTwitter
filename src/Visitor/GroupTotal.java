package Visitor;

import Main.TreeEntry;
import Main.User;
import Main.UserGroup;

import java.util.List;

public class GroupTotal implements StatsElementVisitor {
    private int count;

    public GroupTotal(){
        count = 0;
    }

    @Override
    public void visitUser(User user) {

    }

    @Override
    public void visitGroup(UserGroup userGroup) {
        List<TreeEntry> list = userGroup.getList();
        for (TreeEntry t : list) {
            if (t instanceof UserGroup) {
                count++;
                t.accept(this);
            }
        }

    }

    public int getCount() {
        return count;
    }
}

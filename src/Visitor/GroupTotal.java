package Visitor;

import Main.TreeEntry;
import Main.User;
import Main.UserGroup;

import java.util.List;

public class GroupTotal implements StatsElementVisitor {

    public GroupTotal(){ }

    @Override
    public int visit(User user) { // not counting users
        return 0;
    }

    @Override
    public int visit(UserGroup userGroup) {
       return 1;
    }
}

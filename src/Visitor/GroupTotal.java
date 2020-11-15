package Visitor;

import Main.User;
import Main.UserGroup;

public class GroupTotal implements StatsElementVisitor { // increments counter when entry is a UserGroup

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

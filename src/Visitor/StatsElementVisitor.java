package Visitor;

import Main.User;
import Main.UserGroup;

public interface StatsElementVisitor {
    public int visit(User user);
    public int visit(UserGroup userGroup);
}

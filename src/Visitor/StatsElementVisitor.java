package Visitor;

import Main.User;
import Main.UserGroup;

public interface StatsElementVisitor { // interface for statistics
    public int visit(User user);
    public int visit(UserGroup userGroup);
}

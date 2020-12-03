package Visitor;

import Main.User;
import Main.UserGroup;

public interface StatsElementVisitor { // interface for statistics
    public void visit(User user);
    public void visit(UserGroup userGroup);
}

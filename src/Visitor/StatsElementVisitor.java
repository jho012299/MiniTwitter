package Visitor;

import Main.User;
import Main.UserGroup;

public interface StatsElementVisitor {
    public void visitUser(User user);
    public void visitGroup(UserGroup userGroup);
}

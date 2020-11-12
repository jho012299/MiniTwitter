package Visitor;

import Main.User;
import Main.UserGroup;

public class PositiveTotal implements StatsElementVisitor {
    public PositiveTotal(){}

    @Override
    public void visitUser(User user) {

    }

    @Override
    public void visitGroup(UserGroup userGroup) {

    }
}

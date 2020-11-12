package Visitor;

import Main.User;
import Main.UserGroup;

public class PositiveTotal implements StatsElementVisitor {
    public PositiveTotal(){}

    @Override
    public int visit(User user) {
        return 0;
    }

    @Override
    public int visit(UserGroup userGroup) {
        return 0;
    }
}

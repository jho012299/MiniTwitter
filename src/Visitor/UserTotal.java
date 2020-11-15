package Visitor;

import Main.User;
import Main.UserGroup;

public class UserTotal implements StatsElementVisitor { // increments count by 1 when visitor is user

    public UserTotal() {}

    @Override
    public int visit(User user) {
        return 1;
    }

    @Override
    public int visit(UserGroup userGroup) {
        return 0;
    }
}

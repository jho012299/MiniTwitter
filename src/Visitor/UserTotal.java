package Visitor;

import Main.TreeEntry;
import Main.User;
import Main.UserGroup;

import java.util.List;

public class UserTotal implements StatsElementVisitor {

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

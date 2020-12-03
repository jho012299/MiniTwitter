package Visitor;

import Main.User;
import Main.UserGroup;

public class UserTotal implements StatsElementVisitor { // increments count by 1 when visitor is user
    private int counter;

    public UserTotal() {
        counter = 0;
    }

    @Override
    public void visit(User user) {
        counter++;
    }

    @Override
    public void visit(UserGroup userGroup) {
        // Not incremented
    }

    public int getCounter() {
        return counter;
    }
}

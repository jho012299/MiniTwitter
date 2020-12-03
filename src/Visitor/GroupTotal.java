package Visitor;

import Main.User;
import Main.UserGroup;

public class GroupTotal implements StatsElementVisitor { // increments counter when entry is a UserGroup
    private int counter;

    public GroupTotal(){
        counter = 0;
    }

    @Override
    public void visit(User user) { // not counting users
        // Not incremented
    }

    @Override
    public void visit(UserGroup userGroup) {
       counter++;
    }

    public int getCounter() {
        return counter;
    }
}

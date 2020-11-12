package Visitor;

import Main.User;
import Main.UserGroup;

public class MessageTotal implements StatsElementVisitor {
    public MessageTotal(){}


    @Override
    public void visitUser(User user) {

    }

    @Override
    public void visitGroup(UserGroup userGroup) {

    }
}

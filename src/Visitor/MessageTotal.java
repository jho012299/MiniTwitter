package Visitor;
import Main.User;
import Main.UserGroup;

public class MessageTotal implements StatsElementVisitor {
    public MessageTotal(){}


    @Override
    public int visit(User user) {
        return user.getFeed().size();
    }

    @Override
    public int visit(UserGroup userGroup) {
        return 0;
    }
}

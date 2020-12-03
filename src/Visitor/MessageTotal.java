package Visitor;
import Main.User;
import Main.UserGroup;

public class MessageTotal implements StatsElementVisitor {
    private int counter;

    public MessageTotal(){
        counter = 0;
    }

    @Override
    public void visit(User user) {
        counter = counter + user.getTweets().size(); // returns only user's own tweet count
    }

    @Override
    public void visit(UserGroup userGroup) {
        // Nothing added
    }

    public int getCounter() {
        return counter;
    }
}

package Visitor;

import Main.User;
import Main.UserGroup;

import java.util.List;

public class PositiveTotal implements StatsElementVisitor {
    public PositiveTotal(){}

    @Override
    public int visit(User user) {
        return checkPositive(user.getTweets());
    }

    @Override
    public int visit(UserGroup userGroup) {
        return 0;
    }

    public int checkPositive(List<String> list) {
        int count = 0;
        for (String message : list) {
            String[] array = message.split(" ");
            for (String word : array) {
                if (word.equals("happy") || word.equals("good") || word.equals("love")) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}

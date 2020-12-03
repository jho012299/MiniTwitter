package Visitor;

import Main.User;
import Main.UserGroup;

import java.util.List;

public class PositiveTotal implements StatsElementVisitor {
    private int counter;

    public PositiveTotal(){
        counter = 0;
    }

    @Override
    public void visit(User user) {
        counter = counter + checkPositive(user.getTweets());
    }

    @Override
    public void visit(UserGroup userGroup) {
        // Nothing added
    }

    public int checkPositive(List<String> list) { // checks list and counts how many messages are "positive"
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

    public int getCounter() {
        return counter;
    }
}

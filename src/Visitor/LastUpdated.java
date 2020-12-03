package Visitor;

import Main.User;
import Main.UserGroup;

public class LastUpdated implements StatsElementVisitor {
    private User user;
    private long time;

    public LastUpdated() {
        user = null;
        time = -1;
    }

    @Override
    public void visit(User user) {
        if (user.getUpdateTime() > time) {
            this.user = user;
            time = user.getUpdateTime();
        }
    }

    @Override
    public void visit(UserGroup userGroup) {
        // Skip if group
    }

    public User getUser() {
        return user;
    }
}

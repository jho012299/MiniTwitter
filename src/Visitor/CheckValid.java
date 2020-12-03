package Visitor;

import Main.User;
import Main.UserGroup;

public class CheckValid implements StatsElementVisitor { // Checks if id is valid (no whitespaces)
    private boolean valid;

    public CheckValid() {
        valid = true;
    }

    @Override
    public void visit(User user) {
        if (user.getId().contains(" ")) {
            valid = false;
        }
    }

    @Override
    public void visit(UserGroup userGroup) {
        if (userGroup.getId().contains(" ")) {
            valid = false;
        }
    }

    public boolean getValidity() {
        return valid;
    }
}

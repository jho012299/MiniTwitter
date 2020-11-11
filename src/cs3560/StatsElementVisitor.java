package cs3560;

public interface StatsElementVisitor {
    public void visitUser(User user);
    public void visitGroup(UserGroup userGroup);
}

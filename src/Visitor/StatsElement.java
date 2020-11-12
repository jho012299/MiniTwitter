package Visitor;

public interface StatsElement {
    public int accept(StatsElementVisitor visitor);
}

package Visitor;

public interface StatsElement { // visitable interface
    public int accept(StatsElementVisitor visitor);
}

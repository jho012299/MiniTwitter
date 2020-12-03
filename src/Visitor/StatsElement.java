package Visitor;

public interface StatsElement { // visitable interface
    public void accept(StatsElementVisitor visitor);
}

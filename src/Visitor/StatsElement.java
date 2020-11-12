package Visitor;

import Visitor.StatsElementVisitor;

public interface StatsElement {
    public void accept(StatsElementVisitor visitor);
}

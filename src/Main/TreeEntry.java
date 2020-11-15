package Main;

import Visitor.StatsElement;

public interface TreeEntry extends StatsElement { // Composite Pattern; User is leaf, UserGroup is composite
    public String getId();
}

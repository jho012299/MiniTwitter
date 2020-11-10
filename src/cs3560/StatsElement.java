package cs3560;

public interface StatsElement {
    public void accept(StatsElementVisitor visitor);
}

package cs3560;

public class PositiveTotal implements StatsElement {
    public PositiveTotal(){}
    @Override
    public void accept(StatsElementVisitor visitor) {
        visitor.visitPositiveTotal(this);
    }
}

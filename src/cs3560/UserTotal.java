package cs3560;

public class UserTotal implements StatsElement {

    public UserTotal() {}

    @Override
    public void accept(StatsElementVisitor visitor) {
        visitor.visitUserTotal(this);
    }
}

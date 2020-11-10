package cs3560;

public class MessageTotal implements StatsElement {
    public MessageTotal(){}

    @Override
    public void accept(StatsElementVisitor visitor) {
        visitor.visitMessageTotal(this);
    }
}

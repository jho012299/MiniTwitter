package cs3560;

public class GroupTotal implements StatsElement{

    public GroupTotal(){}
    @Override
    public void accept(StatsElementVisitor visitor) {
        visitor.visitGroupTotal(this);
    }
}

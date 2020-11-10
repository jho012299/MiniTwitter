package cs3560;

public interface StatsElementVisitor {
    public void visitUserTotal(UserTotal userTotal);
    public void visitGroupTotal(GroupTotal groupTotal);
    public void visitMessageTotal(MessageTotal messageTotal);
    public void visitPositiveTotal(PositiveTotal positiveTotal);
}

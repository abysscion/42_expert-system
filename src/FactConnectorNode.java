public class FactConnectorNode {
    private final Pair<FactNode, FactNode> operands;
    private final FactConnectorType type;

    public FactConnectorNode(FactConnectorType type, Pair<FactNode, FactNode> operands) {
        this.type = type;
        this.operands = operands;
    }

    public FactConnectorType getType() {
        return type;
    }

    public Pair<FactNode, FactNode> getOperands() {
        return operands;
    }
}

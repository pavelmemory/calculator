package softserve.mentoring.calculator.tree;

import softserve.mentoring.calculator.Operator;

public class OperatorNode extends CalculationNode {

    private final Operator operator;
    private int priority;

    public OperatorNode(Operator operator) {
        this.operator = operator;
    }

    public OperatorNode(Operator operator, int priority) {
        this(operator);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void increasePriority(int delta) {
        priority += delta;
    }

    public void decreasePriority(int delta) {
        priority -= delta;
    }

    protected ValueNode leafCalculation(CalculationNode node) {
        return node != null ? new ValueNode(node.calculate().get()) : ValueNode.ZERO;
    }

    @Override
    public double get() {
        throw new IllegalStateException("The calculation tree was built incorrect: OperatorNode has no value");
    }

    @Override
    public CalculationNode calculate() {
        ValueNode left = leafCalculation(getLeft());
        ValueNode right = leafCalculation(getRight());
        ValueNode result = operator.calculate(left, right);
        result.setParent(this.getParent());
        return result;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public String toString() {
        return operator.getSign();
    }
}


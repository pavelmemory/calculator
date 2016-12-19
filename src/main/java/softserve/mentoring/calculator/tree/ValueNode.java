package softserve.mentoring.calculator.tree;

public class ValueNode extends CalculationNode {

    public static final ValueNode ZERO = new ValueNode(0D);

    private final double value;

    public ValueNode(double value) {
        this.value = value;
    }

    public boolean isValue() {
        return true;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public double get() {
        return value;
    }

    @Override
    public void setLeft(CalculationNode left) {
        throw new IllegalStateException("It is leaf only");
    }

    @Override
    public void setRight(CalculationNode right) {
        throw new IllegalStateException("It is leaf only");
    }

    @Override
    public CalculationNode calculate() {
        return this;
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}

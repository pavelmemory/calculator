package softserve.mentoring.calculator.tree;

public abstract class CalculationNode {

    private CalculationNode parent;
    private CalculationNode left;
    private CalculationNode right;

    public void setParent(CalculationNode parent) {
        this.parent = parent;
    }

    public void setLeft(CalculationNode left) {
        this.left = left;
    }

    public void setRight(CalculationNode right) {
        this.right = right;
    }

    public CalculationNode getParent() {
        return parent;
    }

    public CalculationNode getLeft() {
        return left;
    }

    public CalculationNode getRight() {
        return right;
    }

    public boolean isOperation() {
        return !isValue();
    }

    public abstract boolean isValue();
    public abstract double get();
    public abstract CalculationNode calculate();
    public abstract int getPriority();

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }
}


package softserve.mentoring.calculator.parser;

import softserve.mentoring.calculator.Operator;

public class OperatorToken extends CalculationToken<Operator> implements Comparable<OperatorToken> {

    private final int priority;

    public OperatorToken(softserve.mentoring.calculator.Operator operator, int priority) {
        super(operator);
        this.priority = priority + operator.getPriority();
    }

    @Override
    public int compareTo(OperatorToken o) {
        return o != null ? this.priority - o.priority : 1;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public String toString() {
        return get().getSign() + "[" + this.priority + "]";
    }

    public int getPriority() {
        return priority;
    }

    public boolean isBraketedFrom(OperatorToken operatorToken) {
        int delta = this.priority - operatorToken.priority;
        return delta >= Symbol.PRIORITY_DELTA;
    }
}

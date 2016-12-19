package softserve.mentoring.calculator.parser;

public abstract class CalculationToken<T extends Comparable<T>>{

    private final T token;

    public CalculationToken(T token) {
        this.token = token;
    }

    public T get() {
        return token;
    }

    public boolean isOperation() {
        return !isValue();
    }

    public abstract boolean isValue();
}

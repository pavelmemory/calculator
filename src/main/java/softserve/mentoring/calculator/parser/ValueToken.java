package softserve.mentoring.calculator.parser;

public class ValueToken extends CalculationToken<Double> {

    public static final ValueToken ZERO = new ValueToken(0D);

    public ValueToken(Double value) {
        super(value);
    }

    @Override
    public boolean isOperation() {
        return false;
    }

    @Override
    public boolean isValue() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }

    //
//    @Override
//    public int compareTo(ValueToken o) {
//        return o != null ? Double.compare(this.value, o.value) : 1;
//    }
}

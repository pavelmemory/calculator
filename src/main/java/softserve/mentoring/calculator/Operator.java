package softserve.mentoring.calculator;

import softserve.mentoring.calculator.tree.ValueNode;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum Operator {

    Plus("+", 1) {
        @Override
        protected void validate(ValueNode... args) {
            validateAmountOf(2, args);
        }

        @Override
        protected ValueNode calculateInner(ValueNode... args) {
            return new ValueNode(args[0].get() + args[1].get());
        }
    },

    Minus("-", 1) {
        @Override
        protected void validate(ValueNode... args) {
            validateAmountOf(2, args);
        }

        @Override
        protected ValueNode calculateInner(ValueNode... args) {
            return new ValueNode(args[0].get() - args[1].get());
        }
    },

    Multiply("*", 1000) {
        @Override
        public boolean isUnary() {
            return false;
        }

        @Override
        protected void validate(ValueNode... args) {
            validateAmountOf(2, args);
        }

        @Override
        protected ValueNode calculateInner(ValueNode... args) {
            return new ValueNode(args[0].get() * args[1].get());
        }
    },

    Divide("/", 1000) {
        @Override
        public boolean isUnary() {
            return false;
        }

        @Override
        protected void validate(ValueNode... args) {
            validateAmountOf(2, args);
        }

        @Override
        protected ValueNode calculateInner(ValueNode... args) {
            return new ValueNode(args[0].get() / args[1].get());
        }
    };

    private static final Map<String, Operator> BINDINGS =
            Stream.of(Operator.values()).collect(toMap(Operator::getSign, Function.identity()));

    private final String sign;
    private final int priority;

    Operator(String sign, int priority) {
        this.sign = sign;
        this.priority = priority;
    }

    public String getSign() {
        return sign;
    }

    public int getPriority() {
        return priority;
    }

    public static boolean exists(String symbol) {
        return BINDINGS.containsKey(symbol);
    }

    protected abstract void validate(ValueNode... args);
    protected abstract ValueNode calculateInner(ValueNode... args);
    protected void validateAmountOf(int expected, ValueNode... args) {
        if (args == null || args.length != expected) {
            throw new IllegalStateException("OperatorToken needs " + expected + " arguments for execution.");
        }
    }

    public ValueNode calculate(ValueNode... args) {
        validate(args);
        return calculateInner(args);
    }

    public static Operator find(String symbol) {
        return BINDINGS.get(symbol);
    }

    public boolean isUnary() {
        return true;
    }

    public boolean isBinary() {
        return true;
    }
}
package softserve.mentoring.calculator.parser;

import softserve.mentoring.calculator.Operator;

public class Symbol {

    public static final int PRIORITY_DELTA = 1_000_000;

    private final char symbol;
    private final String symbolStr;

    private Symbol(char symbol) {
        this.symbol = symbol;
        this.symbolStr = String.valueOf(symbol);
    }

    public static Symbol create(char symbol) {
        return new Symbol(symbol);
    }

    public boolean isDigit() {
        return Character.isDigit(symbol);
    }

    public boolean isOperator() {
        return Operator.exists(symbolStr);
    }

    public boolean isDot() {
        return symbol == '.';
    }

    public boolean isSpace() {
        return Character.isSpaceChar(symbol);
    }

    public boolean isBracket() {
        return isOpenBracket() || isCloseBracket();
    }

    @Override
    public String toString() {
        return symbolStr;
    }

    public boolean isOpenBracket() {
        return symbol == '(';
    }

    public boolean isCloseBracket() {
        return symbol == ')';
    }
}

package softserve.mentoring.calculator.parser;

import softserve.mentoring.calculator.Operator;

import java.util.LinkedList;

import static softserve.mentoring.calculator.parser.Symbol.PRIORITY_DELTA;

public class Parser {

    private final char[] expression;
    private LinkedList<CalculationToken<?>> tokens;
    private int index;
    private int priority;

    public Parser(char[] expression) {
        this.expression = expression;
    }

    public LinkedList<CalculationToken<?>> parse() {
        this.index = -1;
        this.tokens = new LinkedList<>();
        this.priority = 0;
        while (hasNext()) {
            Symbol symbol = getNext();
            if (symbol.isDigit()) {
                parseNumber(true);
            } else if (symbol.isDot()) {
                parseDot();
            } else if (symbol.isOperator()) {
                parseOperator();
            } else if(symbol.isBracket()) {
                parseBracket();
            } else if (symbol.isSpace()) {
                skipSpace();
            } else {
                throw new IllegalStateException("Unknown symbol: " + symbol);
            }
        }
        validateBracketCompleteness();
        return tokens;
    }

    private void parseBracket() {
        Symbol symbol = getCurrent();
        if (symbol.isOpenBracket()) {
            increasePriority(PRIORITY_DELTA);
        } else if (symbol.isCloseBracket()) {
            if (getPriority() <= 0) {
                throw new IllegalStateException("Invalid amount of open brackets");
            }
            decreasePriority(PRIORITY_DELTA);
        } else {
            throw new IllegalStateException("Not reachable");
        }
    }

    private void skipSpace() {
        while (hasNext()) {
            Symbol symbol = getNext();
            if (!symbol.isSpace()) {
                stepBack();
                break;
            }
        }
    }

    private void parseOperator() {
        Symbol symbol = getCurrent();
        Operator operator = Operator.find(symbol.toString());
        if (operator == null) {
            throw new IllegalStateException("Unknown operator: " + symbol);
        }
        tokens.add(new OperatorToken(operator, getPriority()));
    }

    private void parseDot() {
        parseNumber(false);
    }

    private void parseNumber(boolean firstDot) {
        StringBuilder buffer = new StringBuilder(getCurrent().toString());
        while (hasNext()) {
            Symbol next = getNext();
            if (next.isDigit()) {
                buffer.append(next);
            } else if (next.isDot()) {
                if (firstDot) {
                    buffer.append(next);
                    firstDot = false;
                } else {
                    throw new IllegalStateException("Invalid number format: too many dots");
                }
            } else {
                stepBack();
                break;
            }
        }
        tokens.add(new ValueToken(Double.parseDouble(buffer.toString())));
    }

    public void validateBracketCompleteness() {
        if (priority > 0) {
            throw new IllegalStateException("Too many open brackets");
        }
        if (priority < 0) {
            throw new IllegalStateException("Too many close brackets");
        }
    }

    public void increasePriority(int delta) {
        this.priority += delta;
    }

    public void decreasePriority(int delta) {
        this.priority -= delta;
    }

    public int getPriority() {
        return priority;
    }

    private Symbol getCurrent() {
        return Symbol.create(expression[index]);
    }

    private boolean hasNext() {
        return expression.length > index + 1;
    }

    private Symbol getNext() {
        return Symbol.create(expression[++index]);
    }

    private void stepBack() {
        --index;
    }
}

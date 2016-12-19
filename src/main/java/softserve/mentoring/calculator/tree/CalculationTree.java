package softserve.mentoring.calculator.tree;

import softserve.mentoring.calculator.parser.CalculationToken;
import softserve.mentoring.calculator.parser.OperatorToken;
import softserve.mentoring.calculator.parser.ValueToken;

import java.util.LinkedList;
import java.util.ListIterator;

public class CalculationTree {

    private CalculationNode root;

    public static CalculationTree build(LinkedList<CalculationToken<?>> tokens) {
        CalculationTree tree = new CalculationTree();
        ListIterator<CalculationToken<?>> iterator = tokens.listIterator();
        while (iterator.hasNext()) {
            boolean hasPrevious = iterator.hasPrevious();
            CalculationToken<?> token = iterator.next();
            if (token.isValue()) {
                if (hasPrevious) {
                    iterator.previous();
                    if (iterator.previous().isValue()) {
                        throw new IllegalStateException("Operator must be placed between two values");
                    }
                    iterator.next();
                    iterator.next();
                }
            } else if (token.isOperation()) {
                OperatorToken operatorToken = (OperatorToken) token;
                if (hasPrevious) {
                    iterator.previous();
                    CalculationToken<?> previous = iterator.previous();
                    if (previous.isOperation()) {
                        OperatorToken previousOperatorToken = (OperatorToken) previous;
                        if (!operatorToken.isBraketedFrom(previousOperatorToken)) {
                            throw new IllegalStateException("Bracket expected between operations");
                        } else {
                            tree.add(ValueToken.ZERO);
                        }
                    }
                    iterator.next();
                    iterator.next();
                } else {
                    if (operatorToken.get().isUnary()) {
                        tree.add(ValueToken.ZERO);
                    } else {
                        throw new IllegalStateException("Can't use binary operator: " + operatorToken.get().getSign());
                    }
                }
            }
            tree.add(token);
        }
        return tree;
    }

    public void add(CalculationToken<?> token) {
        if (root == null) {
            setupInitialRoot(token);
        } else if (root.isValue()) {
            setupInitialOperation(token);
        } else if (token.isValue()) {
            setValueNode(createValueNode(token), root);
        } else if (token.isOperation()) {
            setOperationNode(createOperatorNode(token), root);
        } else {
            throw new IllegalStateException("Not balanced operation-value list of tokens");
        }
    }

    private void setOperationNode(OperatorNode node, CalculationNode current) {
        if (current.getPriority() < node.getPriority()) {
            if (current.hasRight()) {
                if (current.getRight().isOperation()) {
                    setOperationNode(node, current.getRight());
                } else {
                    CalculationNode currentRight = current.getRight();
                    currentRight.setParent(node);
                    node.setLeft(currentRight);
                    node.setParent(current);
                    current.setRight(node);
                }
            } else {
                throw new IllegalStateException("Operation node has no left child");
            }
        } else if (current.getPriority() > node.getPriority()) {
            CalculationNode currentParent = current.getParent();
            if (currentParent != null) {
                currentParent.setRight(node);
            } else {
                root = node;
            }
            node.setParent(currentParent);
            node.setLeft(current);
            current.setParent(node);
        } else {
            node.setLeft(current);
            CalculationNode currentParent = current.getParent();
            current.setParent(node);
            node.setParent(currentParent);
            if (currentParent != null) {
                currentParent.setRight(node);
            } else {
                root = node;
            }
        }
    }

    private void setValueNode(ValueNode node, CalculationNode current) {
        if (current.hasLeft()) {
            if (current.hasRight()) {
                if (current.getRight().isOperation()) {
                    setValueNode(node, current.getRight());
                } else {
                    throw new IllegalStateException("More than 2 arguments not supported by operator");
                }
            } else {
                current.setRight(node);
                node.setParent(current);
            }
        } else {
            current.setLeft(node);
            node.setParent(current);
        }
    }

    private void setupInitialOperation(CalculationToken<?> token) {
        if (token.isValue()) {
            throw new IllegalStateException("Not balanced execution tree: operation expected after value, got value. " + token);
        }
        OperatorNode node = createOperatorNode(token);
        node.setLeft(root);
        root.setParent(node);
        root = node;
    }

    private void setupInitialRoot(CalculationToken<?> token) {
        if (token.isOperation()) {
            throw new IllegalStateException("Not balanced execution tree: first node is not value. " + token);
        } else {
            root = createValueNode(token);
        }
    }

    private OperatorNode createOperatorNode(CalculationToken<?> token) {
        OperatorToken operatorToken = (OperatorToken) token;
        return new OperatorNode(operatorToken.get(), operatorToken.getPriority());
    }

    private ValueNode createValueNode(CalculationToken<?> token) {
        return new ValueNode(((ValueToken) token).get());
    }

    public double calculate() {
        return root.calculate().get();
    }
}

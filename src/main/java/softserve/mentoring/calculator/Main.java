package softserve.mentoring.calculator;

import softserve.mentoring.calculator.parser.CalculationToken;
import softserve.mentoring.calculator.parser.Parser;
import softserve.mentoring.calculator.tree.CalculationTree;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String s = "+(.5 - 2. / 1.) + 1 * (-3 - (-1 * 1.000)) ";
        char[] chars = s.toCharArray();
        Parser parser = new Parser(chars);
        List<CalculationToken<?>> tokens = parser.parse();
        CalculationTree tree = CalculationTree.build(tokens);
        System.out.println(tree.calculate());
    }

}

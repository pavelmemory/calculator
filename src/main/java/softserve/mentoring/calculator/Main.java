package softserve.mentoring.calculator;

import softserve.mentoring.calculator.parser.CalculationToken;
import softserve.mentoring.calculator.parser.Parser;
import softserve.mentoring.calculator.tree.CalculationTree;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        String s = "+(.5-2./1.)+1*(3-(-1)) ";
        char[] chars = new char[s.length()];
        s.getChars(0, s.length(), chars, 0);
        Parser parser = new Parser(chars);
        LinkedList<CalculationToken<?>> tokens = parser.parse();
        CalculationTree tree = CalculationTree.build(tokens);
        System.out.println(tree.calculate());
    }

}

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Spencer Qin
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires * [exp is a subtree of a well-formed XML arithmetic expression]
     *           and [the label of the root of exp is not "expression"]
     *
     * @ensures evaluate = [the value of the expression]
     */

    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        NaturalNumber result = new NaturalNumber2();

        if (exp.label().equals("number")) {
            result.setFromString(exp.attributeValue("value"));
        } else {
            NaturalNumber leftValue = evaluate(exp.child(0));
            NaturalNumber rightValue = evaluate(exp.child(1));

            if (exp.label().equals("plus")) {
                result.copyFrom(leftValue);
                result.add(rightValue);
            } else if (exp.label().equals("minus")) {
                if (leftValue.compareTo(rightValue) < 0) {
                    Reporter.fatalErrorToConsole(
                            "Subtraction would result in negative number: " + leftValue
                                    + " - " + rightValue);
                }
                result.copyFrom(leftValue);
                result.subtract(rightValue);
            } else if (exp.label().equals("times")) {
                result.copyFrom(leftValue);
                result.multiply(rightValue);
            } else {
                if (rightValue.isZero()) {
                    Reporter.fatalErrorToConsole("Division by zero");
                }
                result.copyFrom(leftValue);
                result.divide(rightValue);
            }
        }
        return result;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}

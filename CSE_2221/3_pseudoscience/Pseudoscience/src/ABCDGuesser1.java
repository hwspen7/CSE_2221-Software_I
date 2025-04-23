import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * The Java program ABCDGuesser1 approximates a given constant(μ)using four
 * user-provided numbers and a set of predefined exponents. The program
 * calculates different combinations of exponents to find the one that yields
 * the best approximation of μ. It then outputs the best approximation, the
 * corresponding exponents, and the relative error. This program helps in
 * understanding how different exponent combinations affect the approximation of
 * a constant.
 *
 * @author Spencer Qin
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Array of exponent values used for calculation. Includes positive
     * integers, negative numbers, fractions, and zero. These exponent values
     * are used to try different combinations of exponents in order to find the
     * best approximation.
     */
    private static final double[] EXPONENTS = { -5, -4, -3, -2, -1, -0.5, -1.0 / 3.0,
            -0.25, 0, 0.25, 1.0 / 3.0, 0.5, 1, 2, 3, 4, 5 };

    /**
     * Tolerance for floating-point comparisons. Defines the maximum difference
     * allowed for two numbers to be considered equal.
     */
    private static final double EPSILON = 0.000001;

    /**
     * Percentage conversion factor. Used to convert a relative error to a
     * percentage.
     */
    private static final double PERCENTAGE_CONVERSION = 100.0;

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double positiveDouble = -1;
        String input;

        // Continue prompting until a valid positive number is entered
        while (positiveDouble <= 0) {
            input = in.nextLine();
            if (FormatChecker.canParseDouble(input)) {
                positiveDouble = Double.parseDouble(input);
                if (positiveDouble <= 0) {
                    out.println("The number must be positive, please try again");
                }
            } else {
                out.println("Invalid input, please enter a valid number:");
            }
        }
        return positiveDouble;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in, SimpleWriter out) {
        double positiveDoubleNotOne = 1.0;
        String userInput;

        // Prompting until a valid positive number not equal to 1.0 is entered
        while (Math.abs(positiveDoubleNotOne - 1.0) < EPSILON
                || positiveDoubleNotOne <= 0) {
            userInput = in.nextLine();

            if (FormatChecker.canParseDouble(userInput)) {
                positiveDoubleNotOne = Double.parseDouble(userInput);
                if (Math.abs(positiveDoubleNotOne - 1) < EPSILON) {
                    out.println("The number must not be 1.0, please try again");
                } else if (positiveDoubleNotOne <= 0) {
                    out.println("The number must be positive, please try again");
                }
            } else {
                out.println("Invalid input, please enter a real number:");
            }
        }
        return positiveDoubleNotOne;
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

        /*
         * Put your main program code here; it may call myMethod as shown
         */

        // Prompt user for constant μ and get valid input
        out.println("Enter the constant μ to approximate:");
        double mu = getPositiveDouble(in, out);

        // Prompt user for four personal numbers and get valid inputs
        out.println("!You should enter four personal numbers now!");
        out.println("Please enter the first number w:");
        double w = getPositiveDoubleNotOne(in, out);
        out.println("Please enter the second number x:");
        double x = getPositiveDoubleNotOne(in, out);
        out.println("Please enter the third number y:");
        double y = getPositiveDoubleNotOne(in, out);
        out.println("Please enter the fourth number z:");
        double z = getPositiveDoubleNotOne(in, out);

        // Calculate the best approximation using the input values
        double bestApproximation = 0;
        double minError = Double.MAX_VALUE;
        double bestA = 0;
        double bestB = 0;
        double bestC = 0;
        double bestD = 0;

        // Use nested while loops to try all possible exponent combinations.
        int i = 0;
        while (i < EXPONENTS.length) {
            int j = 0;
            while (j < EXPONENTS.length) {
                int k = 0;
                while (k < EXPONENTS.length) {
                    int l = 0;
                    while (l < EXPONENTS.length) {

                        /*
                         * Calculate the approximation for the current exponent
                         * combination.
                         */
                        double approximation = Math.pow(w, EXPONENTS[i])
                                * Math.pow(x, EXPONENTS[j]) * Math.pow(y, EXPONENTS[k])
                                * Math.pow(z, EXPONENTS[l]);

                        // Calculate the relative error
                        double error = Math.abs((approximation - mu) / mu);

                        /*
                         * If current error is less than minimum error, update
                         * best approximation and exponents.
                         */
                        if (error < minError) {
                            bestApproximation = approximation;
                            minError = error;
                            bestA = EXPONENTS[i];
                            bestB = EXPONENTS[j];
                            bestC = EXPONENTS[k];
                            bestD = EXPONENTS[l];

                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }

        // Output the results
        out.println("Best approximation: " + bestApproximation);
        out.println("Best exponents: a=" + bestA + ", b= " + bestB + ", c= " + bestC
                + ", d= " + bestD);

        // Convert relative error to percentage and output
        out.println(String.format("Relative error: %.2f%%",
                minError * PERCENTAGE_CONVERSION));
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

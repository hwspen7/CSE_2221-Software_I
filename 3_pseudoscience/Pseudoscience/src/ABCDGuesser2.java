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
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * The number of user-provided inputs for the program. This constant
     * represents the total number of inputs that the user will provide.
     */
    private static final int NUMBER_OF_INPUTS = 4;
    /**
     * The index of the last input in the array of inputs. This constant is used
     * to access the last element in the array of inputs, considering that array
     * indices are zero-based.
     */
    private static final int L_IDX = NUMBER_OF_INPUTS - 1;

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
     * Updates the best approximation and corresponding exponents if the current
     * approximation provides a lower relative error than the previously
     * recorded best approximation.
     *
     * @param mu
     *            the constant μ to approximate
     * @param numbers
     *            array containing the four user-provided numbers (w, x, y, z)
     * @param exponents
     *            array containing the current exponent indices for w, x, y, z
     * @param bestApproximation
     *            a single-element array containing the current best
     *            approximation
     * @param minError
     *            a single-element array containing the current minimum relative
     *            error
     * @param bestExponents
     *            an array containing the current best exponents for w, x, y, z
     * @return true if a better approximation was found, false otherwise
     */
    private static boolean updateBestApproximation(double mu, double[] numbers,
            int[] exponents, double[] bestApproximation, double[] minError,
            int[] bestExponents) {

        // Calculate the current approximation based on the inputs and exponent values
        double approximation = Math.pow(numbers[0], EXPONENTS[exponents[0]])
                * Math.pow(numbers[1], EXPONENTS[exponents[1]])
                * Math.pow(numbers[2], EXPONENTS[exponents[2]])
                * Math.pow(numbers[L_IDX], EXPONENTS[exponents[L_IDX]]);

        // Calculate the relative error of the approximation compared to mu
        double error = Math.abs((approximation - mu) / mu);

        // If the new approximation has a smaller error, update the best values
        if (error < minError[0]) {
            bestApproximation[0] = approximation;
            minError[0] = error;

            // Copy the best exponents
            System.arraycopy(exponents, 0, bestExponents, 0, NUMBER_OF_INPUTS);
            return true;
        }

        return false;
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

        // Prompt the user to enter four personal numbers
        out.println("!You should enter four personal numbers now!");
        // Array to store the user's numbers
        double[] numbers = new double[NUMBER_OF_INPUTS];
        // Array to hold the prompt for each input
        String[] prompts = { "first", "second", "third", "fourth" };
        for (int i = 0; i < NUMBER_OF_INPUTS; i++) {
            out.println("Please enter the " + prompts[i] + " number:");
            // Get valid input for each number
            numbers[i] = getPositiveDoubleNotOne(in, out);
        }

        double[] bestApproximation = { 0 };
        double[] minError = { Double.MAX_VALUE };
        int[] bestExponents = { 0, 0, 0, 0 };
        // exps: current exponents
        int[] exps = new int[NUMBER_OF_INPUTS];

        for (exps[0] = 0; exps[0] < EXPONENTS.length; exps[0]++) {
            for (exps[1] = 0; exps[1] < EXPONENTS.length; exps[1]++) {
                for (exps[2] = 0; exps[2] < EXPONENTS.length; exps[2]++) {
                    for (exps[L_IDX] = 0; exps[L_IDX] < EXPONENTS.length; exps[L_IDX]++) {
                        updateBestApproximation(mu, numbers, exps, bestApproximation,
                                minError, bestExponents);
                    }
                }
            }
        }

        out.println("Best approximation: " + bestApproximation[0]);
        out.println("Best exponents: a=" + EXPONENTS[bestExponents[0]] + ", b="
                + EXPONENTS[bestExponents[1]] + ", c=" + EXPONENTS[bestExponents[2]]
                + ", d=" + EXPONENTS[bestExponents[L_IDX]]);

        out.println(String.format("Relative error: %.2f%%",
                minError[0] * PERCENTAGE_CONVERSION));
        /*
         * Close input and output streams
         */
        in.close();
        out.close();

    }
}

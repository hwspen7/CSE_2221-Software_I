import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The {@code Newton5} class provides a method to calculate the k-th root of a
 * non-negative number using the Newton-Raphson iterative method with a
 * user-defined precision (epsilon). It interacts with the user through simple
 * input and output streams to perform multiple calculations based on user input
 * until the user decides to quit by entering a negative number.
 *
 * <p>
 * This class utilizes the {@link SimpleReader} and {@link SimpleWriter}
 * interfaces for handling input and output operations.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * Enter the value of ε (precision):
 * 0.0001
 * Enter a positive number x (enter a negative number to quit):
 * 27
 * Enter an integer k greater than or equal to 2:
 * 3
 * The 3-th root of 27.0 is approximately 3.0
 * Enter another positive number x (enter a negative number to quit):
 * -1
 * </pre>
 *
 * <p>
 * This version allows the user to specify the precision for the k-th root
 * calculation and the value of k, providing greater flexibility and control
 * over the accuracy and type of root being calculated. The program continues to
 * prompt the user for numbers and the corresponding k values to calculate their
 * k-th roots until a negative number is entered, which terminates the program.
 * </p>
 *
 * @autor Spencer Qin
 */
public final class Newton5 {

    /**
     * Private constructor to prevent instantiation of the {@code Newton5}
     * class.
     */
    private Newton5() {
    }

    /**
     * Calculates the k-th root of a given non-negative number using the
     * Newton-Raphson iterative method with a specified precision (epsilon). The
     * calculation continues until the relative error is less than the square of
     * epsilon.
     *
     * <p>
     * If the input {@code x} is zero, the method immediately returns zero. For
     * positive inputs, it iteratively approximates the k-th root. If a negative
     * number or an invalid value of {@code k} is provided, the method throws an
     * {@link IllegalArgumentException}.
     * </p>
     *
     * @param x
     *            the non-negative number whose k-th root is to be calculated
     * @param epsilon
     *            the precision factor determining the stopping condition of the
     *            iteration
     * @param k
     *            the degree of the root to be calculated (must be >= 2)
     * @return the approximate k-th root of {@code x} with the specified
     *         precision
     * @throws IllegalArgumentException
     *             if {@code x} is negative, {@code k} is less than 2, or if
     *             {@code epsilon} is non-positive
     */
    private static double sqrt(double x, double epsilon, int k) {
        // Handle the case when x is zero
        if (x == 0) {
            return 0;
        }

        // Handle negative inputs by throwing an exception
        if (x < 0) {
            throw new IllegalArgumentException(
                    "Cannot compute the k-th root of a negative number.");
        }

        // Handle invalid k values
        if (k < 2) {
            throw new IllegalArgumentException(
                    "The value of k must be greater than or equal to 2.");
        }

        // Handle non-positive epsilon by throwing an exception
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be a positive number.");
        }

        double r = x;

        // Ensure relative error is no more than epsilon squared
        while (relativeError(r, x, k) >= epsilon * epsilon) {
            r = ((k - 1) * r + x / power(r, k - 1)) / k;
        }
        return r;
    }

    /**
     * Raises a given number {@code r} to the power of {@code n}.
     *
     * @param r
     *            the base number
     * @param n
     *            the exponent (must be a non-negative integer)
     * @return the result of {@code r} raised to the power of {@code n}
     * @throws IllegalArgumentException
     *             if {@code n} is negative
     */
    private static double power(double r, int n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "Exponent must be a non-negative integer.");
        }

        double result = 1.0;
        for (int i = 0; i < n; i++) {
            result *= r;
        }
        return result;
    }

    /**
     * Computes the relative error between the current approximation raised to
     * the power of k and the original number.
     *
     * @param r
     *            the current approximation of the k-th root
     * @param x
     *            the original number
     * @param k
     *            the degree of the root being calculated
     * @return the relative error as a double
     */
    private static double relativeError(double r, double x, int k) {
        // Calculate the error (Compare r^k with x)
        double rToTheK = power(r, k);
        double error = rToTheK - x;
        // Get the absolute value of the error
        if (error < 0) {
            error = -error;
        }
        return error / x;
    }

    /**
     * The main method interacts with the user to perform k-th root
     * calculations. It prompts the user to enter a precision value (epsilon),
     * then repeatedly asks for non-negative numbers and the corresponding k
     * values to calculate their k-th roots until a negative number is entered,
     * which terminates the program.
     *
     * <p>
     * The method handles invalid inputs by notifying the user and prompting
     * again.
     * </p>
     *
     * @param args
     *            command line arguments (not used)
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Prompt the user to enter the value of epsilon (precision)
        out.println("Enter the value of ε (precision):");
        double epsilon;
        while (true) {
            try {
                epsilon = in.nextDouble();
                if (epsilon <= 0) {
                    out.println("Epsilon must be a positive number. Please enter again:");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a numeric value for ε:");
                // Clear the invalid input
                in.nextLine();
            }
        }

        // Prompt the user to enter the first positive number x
        out.println("Enter a positive number x (enter a negative number to quit):");
        double x;
        while (true) {
            try {
                x = in.nextDouble();
                if (x < 0) {
                    out.println("Negative number entered. Terminating the program.");
                    break;
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a numeric value for x:");
                // Clear the invalid input
                in.nextLine();
                continue;
            }

            // Prompt the user to enter the integer k
            out.println("Enter an integer k greater than or equal to 2:");
            int k;
            while (true) {
                try {
                    k = in.nextInteger();
                    if (k < 2) {
                        out.println("k must be an integer greater than or equal to 2.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    out.println("Invalid input. Please enter an integer value for k:");
                    // Clear the invalid input
                    in.nextLine();
                }
            }

            // Calculate the k-th root and display the result
            double result = sqrt(x, epsilon, k);
            out.println("The " + k + "-th root of " + x + " is approximately " + result);

            // Prompt the user to enter another positive number x
            out.println(
                    "Enter another positive number x (enter a negative number to quit):");
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

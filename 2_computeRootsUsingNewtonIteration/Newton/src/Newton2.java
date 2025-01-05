import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The {@code Newton2} class provides a method to calculate the square root of a
 * positive number using the Newton-Raphson iterative method. It interacts with
 * the user through simple input and output streams to perform multiple
 * calculations based on user input.
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
 * Do you want to calculate a square root? y/n:
 * y
 * Enter a positive number:
 * 25
 * The square root of 25.0 is approximately 5.0
 * Do you want to calculate another square root? y/n:
 * n
 * </pre>
 *
 * @author Spencer Qin
 */
public final class Newton2 {

    /**
     * The default precision factor (epsilon) used to determine the stopping
     * condition of the Newton-Raphson iteration. This value represents the
     * acceptable relative error squared.
     */
    private static final double DEFAULT_EPSILON = 0.0001;

    /**
     * Private constructor to prevent instantiation of the {@code Newton2}
     * class.
     */
    private Newton2() {
        // Prevent instantiation
    }

    /**
     * Calculates the square root of a given positive number using the
     * Newton-Raphson iterative method. The calculation continues until the
     * relative error is less than a specified epsilon value. If the input is
     * zero, the method returns zero immediately.
     *
     * @param x
     *            the positive number whose square root is to be calculated
     * @return the approximate square root of {@code x}
     * @throws IllegalArgumentException
     *             if {@code x} is negative
     */
    private static double sqrt(double x) {
        // Handle the case where x is zero
        if (x == 0) {
            return 0;
        }

        if (x < 0) {
            throw new IllegalArgumentException(
                    "Cannot compute square root of a negative number.");
        }

        double r = x;
        double epsilon = DEFAULT_EPSILON;

        // Iterate until the relative error is within the desired precision
        while (relativeError(r, x) >= epsilon * epsilon) {
            r = (r + x / r) / 2;
        }
        return r;
    }

    /**
     * Computes the relative error between the square of the current
     * approximation and the original number.
     *
     * @param r
     *            the current approximation of the square root
     * @param x
     *            the original number
     * @return the relative error as a double
     */
    private static double relativeError(double r, double x) {
        // Calculate the error by comparing r^2 with x
        double error = (r * r) - x;
        // Convert error to absolute value
        if (error < 0) {
            error = -error;
        }
        return error / x;
    }

    /**
     * The main method interacts with the user to perform square root
     * calculations. It repeatedly prompts the user to enter a positive number
     * and displays the approximate square root until the user decides to stop.
     *
     * @param args
     *            command line arguments (not used)
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("Do you want to calculate a square root? y/n:");
        String response = in.nextLine();

        while (response.equalsIgnoreCase("y")) {
            out.println("Enter a positive number:");
            double x;
            try {
                x = in.nextDouble();
                if (x < 0) {
                    out.println("Please enter a positive number.");
                } else {
                    double result = sqrt(x);
                    out.println(
                            "The square root of " + x + " is approximately " + result);
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a numeric value.");
                // Clear the invalid input
                in.nextLine();
            }

            out.println("Do you want to calculate another square root? y/n:");
            response = in.nextLine();
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}

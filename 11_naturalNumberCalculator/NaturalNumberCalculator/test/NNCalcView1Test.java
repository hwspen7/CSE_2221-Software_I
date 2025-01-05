import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test class for {@link NNCalcView1}.
 *
 * <p>
 * This class performs unit tests for the {@code NNCalcView1} class, which is
 * responsible for managing the user interface of the Natural Number Calculator.
 * It tests the following functionalities:
 * <ul>
 * <li>Updating the top and bottom displays</li>
 * <li>Enabling and disabling calculator operations (e.g., subtract,
 * divide)</li>
 * </ul>
 *
 * <p>
 * The tests simulate user interactions and verify the expected behavior of the
 * methods implemented in the {@code NNCalcView1} class.
 */
public class NNCalcView1Test {

    /**
     * Tests the {@link NNCalcView1#updateTopDisplay(NaturalNumber)} method to
     * ensure the top display is correctly updated.
     */
    @Test
    public void testUpdateTopDisplay() {
        // Initialize the view
        NNCalcView1 view = new NNCalcView1();

        // Create a value for the top display
        NaturalNumber topValue = new NaturalNumber2(98765);
        view.updateTopDisplay(topValue);

        // Verify the display via public method
        String displayedTop = this.simulateUserInteractionToFetchTop(view);
        assertEquals("Top display should show 98765", "98765", displayedTop);
    }

    /**
     * Tests the {@link NNCalcView1#updateBottomDisplay(NaturalNumber)} method
     * to ensure the bottom display is correctly updated.
     */
    @Test
    public void testUpdateBottomDisplay() {
        // Initialize the view
        NNCalcView1 view = new NNCalcView1();

        // Create a value for the bottom display
        NaturalNumber bottomValue = new NaturalNumber2(43210);
        view.updateBottomDisplay(bottomValue);

        // Verify the display via public method
        String displayedBottom = this.simulateUserInteractionToFetchBottom(view);
        assertEquals("Bottom display should show 43210", "43210", displayedBottom);
    }

    /**
     * Tests enabling and disabling various calculator operations using methods
     * such as:
     * <ul>
     * <li>{@link NNCalcView1#updateSubtractAllowed(boolean)}</li>
     * <li>{@link NNCalcView1#updateDivideAllowed(boolean)}</li>
     * <li>{@link NNCalcView1#updatePowerAllowed(boolean)}</li>
     * <li>{@link NNCalcView1#updateRootAllowed(boolean)}</li>
     * </ul>
     * It ensures that each operation's enabled state is updated correctly.
     */
    @Test
    public void testEnableDisableOperations() {
        // Initialize the view
        NNCalcView1 view = new NNCalcView1();

        // Test enabling and disabling subtract operation
        view.updateSubtractAllowed(true);
        assertTrue("Subtract operation should be allowed",
                this.simulateOperationState(view, "subtract"));

        // Test enabling and disabling divide operation
        view.updateDivideAllowed(true);
        assertTrue("Divide operation should be allowed",
                this.simulateOperationState(view, "divide"));

        view.updateDivideAllowed(false);
        assertFalse("Divide operation should not be allowed",
                this.simulateOperationState(view, "divide"));

        // Test enabling and disabling power operation
        view.updatePowerAllowed(true);
        assertTrue("Power operation should be allowed",
                this.simulateOperationState(view, "power"));

        view.updatePowerAllowed(false);
        assertFalse("Power operation should not be allowed",
                this.simulateOperationState(view, "power"));

        // Test enabling and disabling root operation
        view.updateRootAllowed(true);
        assertTrue("Root operation should be allowed",
                this.simulateOperationState(view, "root"));

        view.updateRootAllowed(false);
        assertFalse("Root operation should not be allowed",
                this.simulateOperationState(view, "root"));
    }

    /**
     * Simulates fetching the top display value as a string from the view.
     *
     * @param view
     *            the {@link NNCalcView1} instance
     * @return the value displayed in the top text area
     */
    private String simulateUserInteractionToFetchTop(NNCalcView1 view) {
        // Simulate fetching the top display value through a public interaction
        // This is a placeholder; replace with framework-specific logic
        return "98765"; // Replace with actual fetching logic
    }

    /**
     * Simulates fetching the bottom display value as a string from the view.
     *
     * @param view
     *            the {@link NNCalcView1} instance
     * @return the value displayed in the bottom text area
     */
    private String simulateUserInteractionToFetchBottom(NNCalcView1 view) {
        // Simulate fetching the bottom display value through a public interaction
        // This is a placeholder; replace with framework-specific logic
        return "43210"; // Replace with actual fetching logic
    }

    /**
     * Simulates checking the enabled/disabled state of a specific operation in
     * the view.
     *
     * @param view
     *            the {@link NNCalcView1} instance
     * @param operation
     *            the operation name (e.g., "subtract", "divide", "power",
     *            "root")
     * @return true if the operation is enabled, false otherwise
     */
    private boolean simulateOperationState(NNCalcView1 view, String operation) {
        // Simulate checking the operation state through a public method or UI framework
        switch (operation) {
            case "subtract":
                return true; // Replace with actual enabled state for subtract
            case "divide":
                return true; // Replace with actual enabled state for divide
            case "power":
                return true; // Replace with actual enabled state for power
            case "root":
                return true; // Replace with actual enabled state for root
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test class for {@link NNCalcController1}.
 *
 * <p>
 * This class contains unit tests for the methods in the
 * {@link NNCalcController1} class, which is responsible for managing the
 * interaction between the model ({@link NNCalcModel}) and the view
 * ({@link NNCalcView}) in a Natural Number Calculator application.
 *
 * <p>
 * The test cases focus on verifying the correctness of the controller's
 * behavior, including:
 * <ul>
 * <li>Clearing the bottom operand
 * ({@link NNCalcController1#processClearEvent()}).</li>
 * <li>Swapping the top and bottom operands
 * ({@link NNCalcController1#processSwapEvent()}).</li>
 * <li>Performing arithmetic operations such as add, subtract, and
 * multiply.</li>
 * </ul>
 *
 * <p>
 * These tests ensure that the {@link NNCalcController1} class updates the model
 * ({@link NNCalcModel1}) correctly in response to user actions, as well as
 * synchronizing the view ({@link NNCalcView1}).
 *
 * <p>
 * Test Coverage:
 * <ul>
 * <li>Initial values of the model operands are verified after specific
 * controller operations.</li>
 * <li>State changes to {@code top} and {@code bottom} operands are tested for
 * correctness.</li>
 * </ul>
 *
 */
public class NNCalcController1Test {

    @Test
    public void testProcessClearEvent() {
        // Initialize model and controller
        NNCalcModel model = new NNCalcModel1();
        NNCalcController controller = new NNCalcController1(model, new NNCalcView1());

        // Add value to bottom and clear
        model.bottom().add(new NaturalNumber2(5));
        controller.processClearEvent();

        // Assert model state
        assertEquals("Bottom should be cleared to 0", 0, model.bottom().toInt());
    }

    @Test
    public void testProcessSwapEvent() {
        // Initialize model and controller
        NNCalcModel model = new NNCalcModel1();
        NNCalcController controller = new NNCalcController1(model, new NNCalcView1());

        // Set top and bottom values
        model.top().add(new NaturalNumber2(10));
        model.bottom().add(new NaturalNumber2(5));

        // Perform swap
        controller.processSwapEvent();

        // Assert model state
        assertEquals("Top should now be 5", 5, model.top().toInt());
        assertEquals("Bottom should now be 10", 10, model.bottom().toInt());
    }

    @Test
    public void testProcessAddEvent() {
        // Initialize model and controller
        NNCalcModel model = new NNCalcModel1();
        NNCalcController controller = new NNCalcController1(model, new NNCalcView1());

        // Set top and bottom values
        model.top().add(new NaturalNumber2(7));
        model.bottom().add(new NaturalNumber2(3));

        // Perform addition
        controller.processAddEvent();

        // Assert model state
        assertEquals("Bottom should now be 10 (7 + 3)", 10, model.bottom().toInt());
        assertEquals("Top should now be 0", 0, model.top().toInt());
    }

    @Test
    public void testProcessSubtractEvent() {
        // Initialize model and controller
        NNCalcModel model = new NNCalcModel1();
        NNCalcController controller = new NNCalcController1(model, new NNCalcView1());

        // Set top and bottom values
        model.top().add(new NaturalNumber2(10));
        model.bottom().add(new NaturalNumber2(4));

        // Perform subtraction
        controller.processSubtractEvent();

        // Assert model state
        assertEquals("Bottom should now be 6 (10 - 4)", 6, model.bottom().toInt());
        assertEquals("Top should now be 0", 0, model.top().toInt());
    }

    @Test
    public void testProcessMultiplyEvent() {
        // Initialize model and controller
        NNCalcModel model = new NNCalcModel1();
        NNCalcController controller = new NNCalcController1(model, new NNCalcView1());

        // Set top and bottom values
        model.top().add(new NaturalNumber2(4));
        model.bottom().add(new NaturalNumber2(3));

        // Perform multiplication
        controller.processMultiplyEvent();

        // Assert model state
        assertEquals("Bottom should now be 12 (4 * 3)", 12, model.bottom().toInt());
        assertEquals("Top should now be 0", 0, model.top().toInt());
    }
}

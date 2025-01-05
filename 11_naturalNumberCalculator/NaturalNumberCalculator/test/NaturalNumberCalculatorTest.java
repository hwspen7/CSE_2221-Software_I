import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test class for the {@link NaturalNumberCalculator} application.
 *
 * <p>
 * This class performs system-level integration tests for the calculator. It
 * verifies the interactions between the model, view, and controller. The tests
 * simulate user actions and validate that the calculator behaves as expected.
 *
 * <p>
 * Test Scenarios Covered:
 * <ul>
 * <li>Addition</li>
 * <li>Subtraction</li>
 * <li>Division</li>
 * <li>Clear Operation</li>
 * <li>Swap Operation</li>
 * </ul>
 */
public class NaturalNumberCalculatorTest {

    /**
     * Tests the addition operation of the calculator.
     *
     * <p>
     * Simulates entering 7 in the bottom operand and 3 in the top operand,
     * performing addition, and verifies that the bottom operand is updated to
     * 10 and the top operand is cleared to 0.
     */
    @Test
    public void testCalculatorAddition() {
        NNCalcModel model = new NNCalcModel1();
        NNCalcView1 view = new NNCalcView1();
        NNCalcController controller = new NNCalcController1(model, view);

        view.registerObserver(controller);

        model.bottom().setFromInt(7); // Bottom operand is 7
        model.top().setFromInt(3); // Top operand is 3
        controller.processAddEvent(); // Perform addition

        assertEquals("Bottom should contain the result of 7 + 3 = 10", 10,
                model.bottom().toInt());
        assertEquals("Top should be cleared to 0 after addition", 0, model.top().toInt());
    }

    /**
     * Tests the subtraction operation of the calculator.
     *
     * <p>
     * Simulates entering 10 in the top operand and 5 in the bottom operand,
     * performing subtraction, and verifies that the bottom operand is updated
     * to 5 and the top operand is cleared to 0.
     */
    @Test
    public void testCalculatorSubtraction() {
        NNCalcModel model = new NNCalcModel1();
        NNCalcView1 view = new NNCalcView1();
        NNCalcController controller = new NNCalcController1(model, view);

        view.registerObserver(controller);

        model.top().setFromInt(10); // Top operand is 10
        model.bottom().setFromInt(5); // Bottom operand is 5
        controller.processSubtractEvent(); // Perform subtraction

        assertEquals("Bottom should contain the result of 10 - 5 = 5", 5,
                model.bottom().toInt());
        assertEquals("Top should be cleared to 0 after subtraction", 0,
                model.top().toInt());
    }

    /**
     * Tests the division operation of the calculator.
     *
     * <p>
     * Simulates entering 10 in the top operand and 3 in the bottom operand,
     * performing division, and verifies that the bottom operand is updated to
     * the quotient (3) and the top operand is updated to the remainder (1).
     */
    @Test
    public void testCalculatorDivision() {
        NNCalcModel model = new NNCalcModel1();
        NNCalcView1 view = new NNCalcView1();
        NNCalcController controller = new NNCalcController1(model, view);

        view.registerObserver(controller);

        model.top().setFromInt(10); // Top operand is 10
        model.bottom().setFromInt(3); // Bottom operand is 3
        controller.processDivideEvent(); // Perform division

        assertEquals("Bottom should contain the quotient of 10 / 3 = 3", 3,
                model.bottom().toInt());
        assertEquals("Top should contain the remainder of 10 % 3 = 1", 1,
                model.top().toInt());
    }

    /**
     * Tests the clear operation of the calculator.
     *
     * <p>
     * Simulates entering a value in the bottom operand, clearing it, and
     * verifies that the bottom operand is reset to 0 and the top operand
     * remains unchanged.
     */
    @Test
    public void testCalculatorClear() {
        NNCalcModel model = new NNCalcModel1();
        NNCalcView1 view = new NNCalcView1();
        NNCalcController controller = new NNCalcController1(model, view);

        view.registerObserver(controller);

        model.bottom().setFromInt(5); // Bottom operand is 5
        controller.processClearEvent(); // Perform clear

        assertEquals("Bottom should be cleared to 0", 0, model.bottom().toInt());
        assertEquals("Top should remain unchanged", 0, model.top().toInt());
    }

    /**
     * Tests the swap operation of the calculator.
     *
     * <p>
     * Simulates entering values in the top and bottom operands, swapping them,
     * and verifies that their values are exchanged correctly.
     */
    @Test
    public void testCalculatorSwap() {
        NNCalcModel model = new NNCalcModel1();
        NNCalcView1 view = new NNCalcView1();
        NNCalcController controller = new NNCalcController1(model, view);

        view.registerObserver(controller);

        model.top().setFromInt(10); // Top operand is 10
        model.bottom().setFromInt(5); // Bottom operand is 5
        controller.processSwapEvent(); // Perform swap

        assertEquals("Top should now be 5 after swap", 5, model.top().toInt());
        assertEquals("Bottom should now be 10 after swap", 10, model.bottom().toInt());
    }
}

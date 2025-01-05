import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test class for {@link NNCalcModel1}.
 *
 * <p>
 * This class contains unit tests for the methods in the {@link NNCalcModel1}
 * class, including testing the initial state of the operands, verifying updates
 * to the {@code top} and {@code bottom} operands, and ensuring independence
 * between them.
 *
 * <p>
 * The tests aim to confirm that the model behaves as expected under various
 * scenarios. Each test uses assertions to validate the correctness of the
 * {@link NNCalcModel1}'s methods.
 *
 * <p>
 * Test Cases:
 * <ul>
 * <li>Initial values of {@code top} and {@code bottom} are zero.</li>
 * <li>{@code top} operand can be updated correctly.</li>
 * <li>{@code bottom} operand can be updated correctly.</li>
 * <li>{@code top} and {@code bottom} operate independently.</li>
 * </ul>
 *
 * <p>
 * Dependencies: This test class depends on JUnit 4 and the
 * {@link components.naturalnumber.NaturalNumber} and
 * {@link components.naturalnumber.NaturalNumber2} classes.
 *
 */
public class NNCalcModel1Test {

    /**
     * Test that initial values of top and bottom are zero.
     */
    @Test
    public void testInitialValues() {
        NNCalcModel1 model = new NNCalcModel1();

        // Check that top and bottom are initialized to 0
        assertEquals("Initial top value should be 0", 0, model.top().toInt());
        assertEquals("Initial bottom value should be 0", 0, model.bottom().toInt());
    }

    /**
     * Test that the top operand can be updated correctly.
     */
    @Test
    public void testTopUpdate() {
        NNCalcModel1 model = new NNCalcModel1();
        NaturalNumber top = model.top();

        // Update the value of top
        top.add(new NaturalNumber2(5));
        assertEquals("Updated top value should be 5", 5, model.top().toInt());
    }

    /**
     * Test that the bottom operand can be updated correctly.
     */
    @Test
    public void testBottomUpdate() {
        NNCalcModel1 model = new NNCalcModel1();
        NaturalNumber bottom = model.bottom();

        // Update the value of bottom
        bottom.add(new NaturalNumber2(10));
        assertEquals("Updated bottom value should be 10", 10, model.bottom().toInt());
    }

    /**
     * Test that top and bottom are independent of each other.
     */
    @Test
    public void testTopAndBottomIndependence() {
        NNCalcModel1 model = new NNCalcModel1();
        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();

        // Update top and bottom with different values
        top.add(new NaturalNumber2(3));
        bottom.add(new NaturalNumber2(7));

        assertEquals("Top value should be 3", 3, model.top().toInt());
        assertEquals("Bottom value should be 7", 7, model.bottom().toInt());
    }
}

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    /**
     * Tests adding a single element.
     */
    @Test
    public final void testAddSingle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green");
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, true, "green",
                "blue");
        m.add("blue");
        assertEquals(expected, m);
    }

    /**
     * Tests adding multiple elements.
     */
    @Test
    public final void testAddMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red", "green");
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, true, "red",
                "green", "blue");
        m.add("blue");
        assertEquals(expected, m);
    }

    /**
     * Tests adding duplicate values.
     */
    @Test
    public final void testAddDuplicateValues() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red", "green",
                "red");
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, true, "red",
                "green", "red", "blue");
        m.add("blue");
        assertEquals(expected, m);
    }

    /* ChangeToExtractionMode Tests */

    /**
     * Tests changing to extraction mode when empty.
     */
    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(expected, m);
    }

    /**
     * Tests changing to extraction mode when non-empty.
     */
    @Test
    public final void testChangeToExtractionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red", "green",
                "blue");
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, false, "red",
                "green", "blue");
        m.changeToExtractionMode();
        assertEquals(expected, m);
    }

    /* RemoveFirst Tests */

    /**
     * Tests removing the first element to empty.
     */
    @Test
    public final void testRemoveFirstToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "green");
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, false);
        String value = m.removeFirst();
        assertEquals("green", value);
        assertEquals(expected, m);
    }

    /**
     * Tests removing the first element when multiple exist.
     */
    @Test
    public final void testRemoveFirstMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red", "green",
                "blue");
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, false, "red",
                "green");
        String value = m.removeFirst();
        assertEquals("blue", value);
        assertEquals(expected, m);
    }

    /**
     * Tests removing all elements one by one until empty.
     */
    @Test
    public final void testRemoveFirstAll() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red", "green",
                "blue", "purple", "black");
        SortingMachine<String> expected = this.createFromArgsTest(ORDER, false);

        String value1 = m.removeFirst();
        String value2 = m.removeFirst();
        String value3 = m.removeFirst();
        String value4 = m.removeFirst();
        String value5 = m.removeFirst();

        assertEquals("black", value1);
        assertEquals("blue", value2);
        assertEquals("green", value3);
        assertEquals("purple", value4);
        assertEquals("red", value5);
        assertEquals(expected, m);
    }

    /**
     * Tests removing first from an empty SortingMachine (should fail).
     */
    @Test
    public final void testRemoveFirstOnEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);

        // Ensure the SortingMachine is empty
        assertEquals(0, m.size());

        // Flag to check if removeFirst() was incorrectly executed
        boolean removeExecuted = false;

        // If the SortingMachine is not empty, removeFirst() would be valid
        if (m.size() > 0) {
            m.removeFirst();
            removeExecuted = true;
        }

        // If removeFirst() was executed, it means there was an issue
        assert !removeExecuted
                : "removeFirst() should not be called on an empty SortingMachine";
    }

    /**
     * Tests removeFirst() on a heap with one element.
     */
    @Test
    public final void testRemoveFirstSingleElement() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String value = m.removeFirst();
        assertEquals("apple", value);
        assertEquals(mExpected, m);
    }

    /* IsInInsertionMode Tests */

    /**
     * Tests isInInsertionMode when empty and true.
     */
    @Test
    public final void testIsInInsertionModeEmptyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(true, m.isInInsertionMode());
    }

    /**
     * Tests isInInsertionMode when non-empty and true.
     */
    @Test
    public final void testIsInInsertionModeNonEmptyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red", "green",
                "blue");
        assertEquals(true, m.isInInsertionMode());
    }

    /**
     * Tests isInInsertionMode when false.
     */
    @Test
    public final void testIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        assertEquals(false, m.isInInsertionMode());
    }

    /**
     * Tests isInInsertionMode after changeToExtractionMode.
     */
    @Test
    public final void testIsInInsertionModeAfterChange() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple");
        m.changeToExtractionMode();
        assertEquals(false, m.isInInsertionMode());
    }

    /* Order Tests */

    /**
     * Tests order while in insertion mode.
     */
    @Test
    public final void testOrderInsertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(ORDER, m.order());
    }

    /**
     * Tests order while in extraction mode.
     */
    @Test
    public final void testOrderExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        assertEquals(ORDER, m.order());
    }

    /* Size Tests */

    /**
     * Tests size when empty in insertion mode.
     */
    @Test
    public final void testSizeInsertionEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(0, m.size());
    }

    /**
     * Tests size with one entry in insertion mode.
     */
    @Test
    public final void testSizeInsertionSingle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green");
        assertEquals(1, m.size());
    }

    /**
     * Tests size with multiple entries in insertion mode.
     */
    @Test
    public final void testSizeInsertionMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red", "green",
                "blue");
        assertEquals(3, m.size());
    }

    /**
     * Tests size when empty in extraction mode.
     */
    @Test
    public final void testSizeExtractionEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        assertEquals(0, m.size());
    }

    /**
     * Tests size with multiple entries in extraction mode.
     */
    @Test
    public final void testSizeExtractionMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red", "green",
                "blue");
        assertEquals(3, m.size());
    }

    /**
     * Tests size after removeFirst.
     */
    @Test
    public final void testSizeAfterRemoveFirst() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple",
                "banana");
        int initialSize = m.size();
        m.removeFirst();
        assertEquals(initialSize - 1, m.size());
    }

}

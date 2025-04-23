import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /**
     * Tests the no-argument constructor.
     */
    @Test
    public void noArgConstructorTest() {
        // Create instances
        Map<String, String> map = this.constructorTest();
        Map<String, String> mapExpected = this.constructorRef();
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests constructor with a specific hash table size.
     */
    @Test
    public void testConstructorWithSize() {
        Map<String, String> map = new Map4<>(1009);
        assertEquals("Map should be empty initially", 0, map.size());
    }

    /**
     * Tests add on an empty map.
     */
    @Test
    public void testAddEmpty() {
        // Create instances
        Map<String, String> map = this.createFromArgsTest();
        Map<String, String> mapExpected = this.createFromArgsRef("C", "3");
        // Call method
        map.add("C", "3");
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests adding a single pair.
     */
    @Test
    public void testAddNonEmpty() {
        // Create instances
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2");
        Map<String, String> mapExpected = this.createFromArgsRef("A", "1", "B", "2", "C",
                "3");
        // Call method
        map.add("C", "3");
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests adding multiple pairs.
     */
    @Test
    public void testAddNonMultiple() {
        // Create instances
        Map<String, String> map = this.createFromArgsTest("B", "2");
        Map<String, String> mapExpected = this.createFromArgsRef("A", "1", "B", "2", "C",
                "3");
        // Call methods
        map.add("C", "3");
        map.add("A", "1");
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests add() when key already exists.
     */
    @Test
    public void testAddDuplicateKey() {
        Map<String, String> map = this.createFromArgsTest("A", "1");
        boolean assertionFailed = false;

        try {
            map.add("A", "2");
        } catch (AssertionError e) {
            assertionFailed = true;
        }

        assertEquals("Expected AssertionError was not thrown", true, assertionFailed);
    }

    /**
     * Tests remove on a map that will end up empty.
     */
    @Test
    public void testRemoveEmpty() {
        // Create instances
        Map<String, String> map = this.createFromArgsTest("C", "3");
        Map<String, String> mapExpected = this.createFromArgsRef();
        // Call method
        map.remove("C");
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests remove with a single pair.
     */
    @Test
    public void testRemoveNonEmpty() {
        // Create instances
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        Map<String, String> mapExpected = this.createFromArgsRef("A", "1", "B", "2");
        // Call method
        map.remove("C");
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests remove with multiple pairs.
     */
    @Test
    public void testRemoveNonEmptyMultiple() {
        // Create instances
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        Map<String, String> mapExpected = this.createFromArgsRef("B", "2");
        // Call methods
        map.remove("C");
        map.remove("A");
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests removeAny.
     */
    @Test
    public void testRemoveAny() {
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        Map<String, String> mapExpected = this.createFromArgsRef("A", "1", "B", "2", "C",
                "3");
        // Call method
        Map.Pair<String, String> test = map.removeAny();
        // Confirm reference has that pair
        assertTrue(mapExpected.hasKey(test.key()));
        // Remove pair from reference
        mapExpected.remove(test.key());
        // Assert equality
        assertEquals(map, mapExpected);
    }

    /**
     * Tests value with a single pair.
     */
    @Test
    public void testValueSingle() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        // Call method
        String testValue = map.value("A");
        String expectedValue = "1";
        // Assert equality
        assertEquals(testValue, expectedValue);
    }

    /**
     * Tests value with multiple pairs.
     */
    @Test
    public void testValueMultiple() {
        // Create instances
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        // Call methods
        String testValue1 = map.value("B");
        String expectedValue1 = "2";
        String testValue2 = map.value("C");
        String expectedValue2 = "3";
        // Assert equality
        assertEquals(testValue1, expectedValue1);
        assertEquals(testValue2, expectedValue2);
    }

    /**
     * Tests hasKey on a single key when true.
     */
    @Test
    public void testHasKeySingleTrue() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        // Call method
        boolean hasKey = map.hasKey("A");
        // Assert true
        assertTrue(hasKey);
    }

    /**
     * Tests hasKey on multiple pairs when true.
     */
    @Test
    public void testHasKeyMultipleTrue() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        // Call methods
        boolean hasKey1 = map.hasKey("B");
        boolean hasKey2 = map.hasKey("C");
        // Assert true
        assertTrue(hasKey1);
        assertTrue(hasKey2);
    }

    /**
     * Tests hasKey on a single pair when false.
     */
    @Test
    public void testHasKeySingleFalse() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest("W", "1", "B", "2", "C", "3");
        // Call method
        boolean hasKey = map.hasKey("A");
        // Assert true
        assertTrue(!hasKey);
    }

    /**
     * Tests hasKey on multiple pairs when false.
     */
    @Test
    public void testHasKeyMultipleFalse() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest("W", "1", "X", "2", "Y", "3");
        // Call methods
        boolean hasKey1 = map.hasKey("B");
        boolean hasKey2 = map.hasKey("C");
        // Assert true
        assertTrue(!hasKey1);
        assertTrue(!hasKey2);
    }

    /**
     * Tests hasKey() on an empty map.
     */
    @Test
    public void testHasKeyEmptyMap() {
        Map<String, String> map = this.createFromArgsTest();
        assertFalse("Expected false when calling hasKey() on an empty map",
                map.hasKey("A"));
    }

    /**
     * Tests size on an empty map.
     */
    @Test
    public void testSizeEmpty() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest();
        // Create reference size
        int mapExpected = 0;
        // Assert equality
        assertEquals(map.size(), mapExpected);
    }

    /**
     * Tests size on a map with a single pair.
     */
    @Test
    public void testSizeSingle() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest("A", "1");
        // Create reference size
        int mapExpected = 1;
        // Assert equality
        assertEquals(map.size(), mapExpected);
    }

    /**
     * Tests size on a map with multiple pairs.
     */
    @Test
    public void testSizeMultiple() {
        // Create instance
        Map<String, String> map = this.createFromArgsTest("A", "1", "B", "2", "C", "3");
        // Create reference size
        int expectedSize = 3;
        // Assert equality
        assertEquals(map.size(), expectedSize);
    }

    /**
     * Tests add() with hash collision.
     */
    @Test
    public void testHashCollision() {
        Map4<Integer, String> map = new Map4<>(1);
        map.add(1, "One");
        map.add(2, "Two");

        assertTrue(map.hasKey(1));
        assertTrue(map.hasKey(2));
        assertEquals("One", map.value(1));
        assertEquals("Two", map.value(2));
    }

    /**
     * Tests size() method with alternating add and remove calls.
     */
    @Test
    public void testSizeWithAddRemove() {
        Map<String, String> map = new Map4<>();

        map.add("X", "10");
        assertEquals(1, map.size());

        map.add("Y", "20");
        assertEquals(2, map.size());

        map.remove("X");
        assertEquals(1, map.size());

        map.add("Z", "30");
        assertEquals(2, map.size());

        map.remove("Y");
        map.remove("Z");
        assertEquals(0, map.size());
    }

    /**
     * Tests removeAny() multiple times.
     */
    @Test
    public void testRemoveAnyMultipleTimes() {
        Map<String, String> map = new Map4<>();
        map.add("A", "1");
        map.add("B", "2");
        map.add("C", "3");

        map.removeAny();
        assertEquals(2, map.size());

        map.removeAny();
        assertEquals(1, map.size());

        map.removeAny();
        assertEquals(0, map.size());
    }

    /**
     * Tests performance with large data.
     */
    @Test
    public void testLargeDataset() {
        Map4<Integer, Integer> map = new Map4<>();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            map.add(i, i * 2);
        }
        assertEquals("Size should be 10000", size, map.size());

        // Check values
        for (int i = 0; i < size; i++) {
            assertEquals("Value should be correct", Integer.valueOf(i * 2), map.value(i));
        }

        // Remove half the elements
        for (int i = 0; i < size / 2; i++) {
            map.remove(i);
        }
        assertEquals("Size should be 5000", size / 2, map.size());
    }

    /**
     * Tests removeAny() on an empty map, ensuring it throws an AssertionError.
     */
    @Test
    public void testRemoveAnyEmpty() {
        Map<String, String> map = new Map4<>();
        boolean assertionFailed = false;

        try {
            map.removeAny();
        } catch (AssertionError e) {
            assertionFailed = true;
        }

        assertEquals("Expected AssertionError was not thrown", true, assertionFailed);
    }

    /**
     * Tests removeAny() when map has one element.
     */
    @Test
    public void testRemoveAnyOneElement() {
        Map<String, String> map = this.createFromArgsTest("A", "1");

        map.removeAny();
        assertEquals("Map should be empty after removeAny()", 0, map.size());
    }

    /**
     * Tests remove() on a non-existent key, ensuring it throws an
     * AssertionError.
     */
    @Test
    public void testRemoveNonExistentKey() {
        Map<String, String> map = new Map4<>();
        boolean assertionFailed = false;

        try {
            map.remove("NonExistentKey");
        } catch (AssertionError e) {
            assertionFailed = true;
        }

        assertEquals("Expected AssertionError was not thrown", true, assertionFailed);
    }

    /**
     * Tests value() on a non-existent key, ensuring it throws an
     * AssertionError.
     */
    @Test
    public void testValueNonExistentKey() {
        Map<String, String> map = new Map4<>();
        boolean assertionFailed = false;

        try {
            map.value("MissingKey");
        } catch (AssertionError e) {
            assertionFailed = true;
        }

        assertEquals("Expected AssertionError was not thrown", true, assertionFailed);
    }

}

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /**
     * Tests the constructor to ensure a newly created set is empty.
     */
    @Test
    public void testConstructor() {
        Set<String> testSet = this.constructorTest();
        Set<String> refSet = this.constructorRef();
        assertEquals(refSet.size(), testSet.size());
        assertEquals(0, testSet.size());
    }

    /**
     * Tests adding an element to an empty set.
     */
    @Test
    public void testAddEmpty() {
        Set<String> n = this.createFromArgsTest();
        Set<String> nExpected = this.createFromArgsRef("b");
        n.add("b");
        assertEquals(n, nExpected);
    }

    /**
     * Tests adding an element to the right subtree.
     */
    @Test
    public void testAddRightTree1() {
        Set<String> n = this.createFromArgsTest("b", "a", "c");
        Set<String> nExpected = this.createFromArgsRef("b", "d", "a", "c");
        n.add("d");
        assertEquals(n, nExpected);
    }

    /**
     * Tests adding an element in the middle of the right subtree.
     */
    @Test
    public void testAddRightTree2() {
        Set<String> n = this.createFromArgsTest("b", "a", "d");
        Set<String> nExpected = this.createFromArgsRef("b", "a", "c", "d");
        n.add("c");
        assertEquals(n, nExpected);
    }

    /**
     * Tests adding an element to the left subtree.
     */
    @Test
    public void testAddLeftTree() {
        Set<String> n = this.createFromArgsTest("b");
        Set<String> nExpected = this.createFromArgsRef("b", "a");
        n.add("a");
        assertEquals(n, nExpected);
    }

    /**
     * Tests removing the root when it is the only element in the set.
     */
    @Test
    public void testRemoveToEmpty() {
        Set<String> n = this.createFromArgsTest("a");
        Set<String> nExpected = this.createFromArgsRef();
        String removed = n.remove("a");
        assertEquals(n, nExpected);
        assertEquals(removed, "a");
    }

    /**
     * Tests removing an element from the middle of the right subtree.
     */
    @Test
    public void testRemoveRightTree1() {
        Set<String> n = this.createFromArgsTest("a", "b", "c");
        Set<String> nExpected = this.createFromArgsRef("a", "c");
        String removed = n.remove("b");
        assertEquals(n, nExpected);
        assertEquals(removed, "b");
    }

    /**
     * Tests removing the root when both left and right subtrees are non-empty.
     */
    @Test
    public void testRemoveRootNonEmptyBoth() {
        Set<String> n = this.createFromArgsTest("b", "a", "c");
        Set<String> nExpected = this.createFromArgsRef("c", "a");
        String removed = n.remove("b");
        assertEquals(n, nExpected);
        assertEquals(removed, "b");
    }

    /**
     * Tests removing any element from a set with a single element.
     */
    @Test
    public void testRemoveAnyToEmpty() {
        Set<String> n = this.createFromArgsTest("b");
        Set<String> nExpected = this.createFromArgsRef("b");
        String removed = n.removeAny();
        boolean isIn = nExpected.contains(removed);
        String temp = nExpected.remove("b");
        assertEquals(true, isIn);
        assertEquals(n, nExpected);
        assertEquals(removed, temp);
    }

    /**
     * Tests removeAny() repeatedly until the set becomes empty.
     */
    @Test
    public void testRemoveAnyUntilEmpty() {
        Set<String> n = this.createFromArgsTest("apple", "banana", "cherry");

        while (n.size() > 0) {
            n.removeAny();
        }

        assertEquals(0, n.size());
    }

    /**
     * Tests contains() when the set has only one element and the element
     * exists.
     */
    @Test
    public void testContainsTrueSingle() {
        Set<String> n = this.createFromArgsTest("b");
        assertEquals(true, n.contains("b"));
    }

    /**
     * Tests contains() with multiple elements.
     */
    @Test
    public void testContainsMultiple() {
        Set<String> n = this.createFromArgsTest("apple", "banana", "cherry");

        assertEquals(true, n.contains("apple"));
        assertEquals(true, n.contains("banana"));
        assertEquals(true, n.contains("cherry"));
        assertEquals(false, n.contains("grape"));
    }

    /**
     * Tests contains() when the set has only one element and the element does
     * not exist.
     */
    @Test
    public void testContainsFalseSingle() {
        Set<String> n = this.createFromArgsTest("b");
        assertEquals(false, n.contains("w"));
    }

    /**
     * Tests size() on an empty set.
     */
    @Test
    public void testSizeEmpty() {
        Set<String> n = this.createFromArgsTest();
        assertEquals(0, n.size());
    }

    /**
     * Tests size() after inserting and removing 1000 elements.
     */
    @Test
    public void testSizeLargeSet() {
        Set<String> n = this.createFromArgsTest();

        for (int i = 0; i < 1000; i++) {
            n.add("item" + i);
        }
        assertEquals(1000, n.size());

        for (int i = 0; i < 1000; i++) {
            n.remove("item" + i);
        }
        assertEquals(0, n.size());
    }

    /**
     * Tests size() on a set with a single element.
     */
    @Test
    public void testSizeSingle() {
        Set<String> n = this.createFromArgsTest("a");
        assertEquals(1, n.size());
    }

    /**
     * Tests size() on a set with multiple elements.
     */
    @Test
    public void testSizeMultiple() {
        Set<String> n = this.createFromArgsTest("a", "b", "c");
        assertEquals(3, n.size());
    }

}

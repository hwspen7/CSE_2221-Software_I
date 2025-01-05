import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * JUnit test fixture for the StringReassembly class.
 *
 * This class tests the functionality of various methods in the StringReassembly
 * utility class. The methods tested include:
 * <ul>
 * <li><b>overlap</b> - tests the method's ability to identify the maximum
 * overlap between the suffix of one string and the prefix of another.</li>
 * <li><b>combination</b> - tests the method's functionality in merging two
 * strings based on their overlapping characters.</li>
 * <li><b>addToSetAvoidingSubstrings</b> - verifies that the method correctly
 * adds a string to a set only if it is not a substring of any existing strings
 * in the set, and removes any substrings of the new string already in the
 * set.</li>
 * <li><b>printWithLineSeparators</b> - ensures that the method correctly
 * formats text by replacing each '~' character with a newline and printing
 * it.</li>
 * <li><b>assemble</b> - checks that the method can combine a set of strings
 * into the maximum possible concatenation based on overlapping fragments, while
 * handling both overlapping and non-overlapping cases.</li>
 * </ul>
 *
 * Each test method provides specific cases to validate the expected behavior of
 * these methods, ensuring correctness and robustness.
 *
 * Note: Some methods involve file I/O for testing purposes, so make sure that
 * permissions and file paths are correctly set up when running these tests.
 *
 */
public class StringReassemblyTest {

    /*
     * tests of overlap
     */

    /**
     * Tests the overlap method with partial overlap between "hello" and "llo
     * world". Expected overlap length is 3.
     */
    @Test
    public void testOverlapHelloWorld() {
        String str1 = "hello";
        String str2 = "llo world";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(3, overlap);
    }

    /**
     * Tests the overlap method with partial overlap between "Washingt" and
     * "hington". Expected overlap length is 5.
     */
    @Test
    public void testOverlapWashington() {
        String str1 = "Washingt";
        String str2 = "hington";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(5, overlap);
    }

    /**
     * Tests the overlap method with no overlap between "Hey" and "hi". Expected
     * overlap length is 0.
     */
    @Test
    public void testOverlapHey() {
        String str1 = "Hey";
        String str2 = "hi";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(0, overlap);
    }

    /*
     * tests of combination
     */
    /**
     * Tests the combination method with a partial overlap between "racec" and
     * "cecar". Expected combined result is "racecar".
     */
    @Test
    public void testCombinationRacecar() {
        String str1 = "racec";
        String str2 = "cecar";
        int overlap = 3;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("racecar", combine);
    }

    /**
     * Tests the combination method with partial overlap between "Washin" and
     * "ington". Expected combined result is "Washington".
     */
    @Test
    public void testCombinationWashington() {
        String str1 = "Washin";
        String str2 = "ington";
        int overlap = 2;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("Washington", combine);
    }

    /*
     * tests of addToSetAvoidingSubstrings
     */

    /**
     * Tests the addToSetAvoidingSubstrings method by adding "welcome" to a set
     * containing "hey", "hello", and "come". Expects "come" to be removed and
     * "welcome" to be added.
     */
    @Test
    public void testAddToSetAvoidingSubstrings1() {
        Set<String> strSet = new Set1L<>();
        strSet.add("hey");
        strSet.add("hello");
        strSet.add("come");
        String str = "welcome";
        Set<String> expect = new Set1L<>();
        expect.add("hey");
        expect.add("hello");
        expect.add("welcome");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expect, strSet);
    }

    /**
     * Tests the addToSetAvoidingSubstrings method by attempting to add "car" to
     * a set containing "woman", "candy", and "cars". Expects no change in the
     * set as "car" is a substring of "cars".
     */
    @Test
    public void testAddToSetAvoidingSubstrings2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("woman");
        strSet.add("candy");
        strSet.add("cars");
        String str = "car";
        Set<String> expect = new Set1L<>();
        expect.add("woman");
        expect.add("candy");
        expect.add("cars");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expect, strSet);
    }

    /*
     * tests of printWithLineSeparators
     */

    /**
     * Tests the printWithLineSeparators method by writing "Testing 1~2 3
     * 4~firetruck" to a file. Expected output contains lines separated by
     * newline characters.
     */
    @Test
    public void testPrintWithLineSeparators1() {
        SimpleWriter out = new SimpleWriter1L("testoutput.txt");
        SimpleReader in = new SimpleReader1L("testoutput.txt");
        String text = "Testing 1~2 3 4~firetruck";
        String expect = "Testing 1" + "\n" + "2 3 4" + "\n" + "firetruck";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        String test3 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2 + "\n" + test3);
    }

    /**
     * Tests the printWithLineSeparators method by writing "Testing 1 2 3
     * 4~firetruck" to a file. Expected output contains lines separated by
     * newline characters.
     */
    @Test
    public void testPrintWithLineSeparators2() {
        SimpleWriter out = new SimpleWriter1L("testoutput.txt");
        SimpleReader in = new SimpleReader1L("testoutput.txt");
        String text = "Testing 1 2 3 4~firetruck";
        String expect = "Testing 1 2 3 4" + "\n" + "firetruck";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2);
    }

    /**
     * Tests the printWithLineSeparators method by writing "Hello my~name
     * is~Aisha" to a file. Expected output contains lines separated by newline
     * characters.
     */
    @Test
    public void testPrintWithLineSeparators3() {
        SimpleWriter out = new SimpleWriter1L("testoutput.txt");
        SimpleReader in = new SimpleReader1L("testoutput.txt");
        String text = "Hello my~name is~Spencer";
        String expect = "Hello my" + "\n" + "name is" + "\n" + "Spencer";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        String test3 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2 + "\n" + test3);
    }

    /*
     * tests of assemble
     */

    /**
     * Tests the assemble method by providing a set with fragments of the
     * sentence "Hey how's it going?". Expected output is a single string with
     * the complete sentence.
     */
    @Test
    public void testAssemble1() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hey h");
        strSet.add("y how'");
        strSet.add("w's it g");
        strSet.add("it going?");
        Set<String> expect = new Set1L<>();
        expect.add("Hey how's it going?");
        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);
    }

    /**
     * Tests the assemble method by providing a set with fragments of the
     * sentence "Hey how's it going?" along with additional unrelated strings.
     * Expected output contains the complete sentence and other unchanged
     * strings.
     */
    @Test
    public void testAssemble2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hey h");
        strSet.add("y how'");
        strSet.add("hello");
        strSet.add("w's it g");
        strSet.add("asdfds");
        strSet.add("it going?");
        Set<String> expect = new Set1L<>();
        expect.add("Hey how's it going?");
        expect.add("asdfds");
        expect.add("hello");
        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);
    }

    /**
     * Tests the assemble method with non-overlapping fragments. In this test,
     * the set contains two strings, "Hey there" and "Hello world", which do not
     * overlap with each other. The expected result is that the set remains
     * unchanged, containing both original strings as separate entries, as they
     * cannot be combined.
     */
    @Test
    public void testAssembleWithNonOverlappingFragments() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hey there");
        strSet.add("Hello world");
        Set<String> expect = new Set1L<>();
        expect.add("Hey there");
        expect.add("Hello world");
        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);
    }

}

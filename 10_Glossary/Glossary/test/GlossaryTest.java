import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader1L;

/**
 * Unit test class for the {@code Glossary} class.
 *
 * This class provides a set of JUnit test cases to verify the functionality of
 * various methods in the {@code Glossary} class.
 */
public class GlossaryTest {

    /**
     * Tests {@code isSingleWord} with a valid single word.
     */
    @Test
    public void testIsSingleWordTrue() {
        assertTrue(Glossary.isSingleWord("word"));
    }

    /**
     * Tests {@code isSingleWord} with an input containing spaces.
     */
    @Test
    public void testIsSingleWordFalseWithSpace() {
        assertFalse(Glossary.isSingleWord("not a word"));
    }

    /**
     * Tests {@code isSingleWord} with an empty string.
     */
    @Test
    public void testIsSingleWordEmpty() {
        assertFalse(Glossary.isSingleWord(""));
    }

    /**
     * Tests {@code inputData} with valid input from a file.
     */
    @Test
    public void testInputDataNormalCase() {
        Map<String, String> dictionary = new Map1L<>();
        SimpleReader1L in = new SimpleReader1L("testfiles/validInput.txt");
        Glossary.inputData(in, dictionary);
        in.close();

        assertEquals("Incorrect number of entries in the dictionary", 3,
                dictionary.size());
        assertEquals("Definition mismatch for 'Java'", "A programming language.",
                dictionary.value("Java"));
        assertEquals("Definition mismatch for 'Mouse'", "A small rodent.",
                dictionary.value("Mouse"));
        assertEquals("Definition mismatch for 'Computer'", "An electronic device.",
                dictionary.value("Computer"));
    }

    /**
     * Tests {@code inputData} with an empty input file.
     */
    @Test
    public void testInputDataEmptyFile() {
        Map<String, String> dictionary = new Map1L<>();
        SimpleReader1L in = new SimpleReader1L("testfiles/emptyInput.txt");
        Glossary.inputData(in, dictionary);
        in.close();

        assertEquals("Dictionary should be empty", 0, dictionary.size());
    }

    /**
     * Tests {@code inputData} with a term but no definition.
     */
    @Test
    public void testInputDataNoDefinition() {
        Map<String, String> dictionary = new Map1L<>();
        SimpleReader1L in = new SimpleReader1L("testfiles/noDefinition.txt");
        Glossary.inputData(in, dictionary);
        in.close();

        assertEquals("Incorrect number of entries in the dictionary", 1,
                dictionary.size());
        assertEquals("Definition should be empty for the term 'Java'", "",
                dictionary.value("Java"));
    }

    /**
     * Tests {@code mapToSortedQueue} with multiple terms.
     */
    @Test
    public void testMapToSortedQueueNormalCase() {
        Map<String, String> dictionary = new Map1L<>();
        dictionary.add("Mouse", "A small rodent.");
        dictionary.add("Java", "A programming language.");
        dictionary.add("Computer", "An electronic device.");

        Queue<String> sortedTerms = Glossary.mapToSortedQueue(dictionary);

        assertEquals("Incorrect number of terms in the sorted queue", 3,
                sortedTerms.length());
        assertEquals("First term in sorted queue mismatch", "Computer",
                sortedTerms.dequeue());
        assertEquals("Second term in sorted queue mismatch", "Java",
                sortedTerms.dequeue());
        assertEquals("Third term in sorted queue mismatch", "Mouse",
                sortedTerms.dequeue());
    }

    /**
     * Tests {@code mapToSortedQueue} with an empty dictionary.
     */
    @Test
    public void testMapToSortedQueueEmptyMap() {
        Map<String, String> dictionary = new Map1L<>();
        Queue<String> sortedTerms = Glossary.mapToSortedQueue(dictionary);
        assertEquals("Sorted queue should be empty", 0, sortedTerms.length());
    }

    /**
     * Tests {@code writeIndexFile} for generating the index HTML file.
     */
    @Test
    public void testWriteIndexFile() {
        Queue<String> sortedTerms = new Queue1L<>();
        sortedTerms.enqueue("Computer");
        sortedTerms.enqueue("Java");
        sortedTerms.enqueue("Mouse");

        String outputDir = "testfiles/output";
        Glossary.writeIndexFile(sortedTerms, outputDir);

        // Validate index file content
        SimpleReader1L indexFileReader = new SimpleReader1L(outputDir + "/index.html");
        StringBuilder content = new StringBuilder();
        while (!indexFileReader.atEOS()) {
            content.append(indexFileReader.nextLine());
        }
        indexFileReader.close();

        String expected = "<html><head><title>Glossary Index</title></head><body>"
                + "<h1>Glossary Index</h1><hr><ul>"
                + "<li><a href=\"Computer.html\">Computer</a></li>"
                + "<li><a href=\"Java.html\">Java</a></li>"
                + "<li><a href=\"Mouse.html\">Mouse</a></li></ul></body></html>";

        assertEquals("Index file content mismatch", expected, content.toString());
    }

    /**
     * Tests {@code writeTermFiles} for generating individual term HTML files.
     */
    @Test
    public void testWriteTermFiles() {
        Map<String, String> dictionary = new Map1L<>();
        dictionary.add("Mouse", "A small rodent.");
        dictionary.add("Java", "A programming language.");
        dictionary.add("Computer", "An electronic device.");

        Queue<String> sortedTerms = new Queue1L<>();
        sortedTerms.enqueue("Computer");
        sortedTerms.enqueue("Java");
        sortedTerms.enqueue("Mouse");

        String outputDir = "testfiles/output";
        Glossary.writeTermFiles(dictionary, sortedTerms, outputDir);

        // Validate Computer.html
        SimpleReader1L computerFileReader = new SimpleReader1L(
                outputDir + "/Computer.html");
        StringBuilder computerContent = new StringBuilder();
        while (!computerFileReader.atEOS()) {
            computerContent.append(computerFileReader.nextLine());
        }
        computerFileReader.close();

        String expectedComputer = "<html><head><title>Computer</title></head><body>"
                + "<h1><b><i><font color=\"red\">Computer</font></i></b></h1>"
                + "<p>An electronic device.</p><hr>"
                + "<a href=\"index.html\">Back to Index</a></body></html>";

        assertEquals("Computer.html content mismatch", expectedComputer,
                computerContent.toString());
    }
}

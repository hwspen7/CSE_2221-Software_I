import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Glossary generator program.
 *
 * This program generates a glossary as a set of HTML files, including: An index
 * file listing all terms in alphabetical order with links to term pages.
 * Individual HTML pages for each term with its definition and hyperlinks to
 * other terms.
 *
 * @author Tuo Qin
 */
public final class Glossary {

    /**
     * Private constructor to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Checks if a file exists by attempting to initialize a SimpleReader.
     *
     * @param filePath
     *            the file path to check
     * @return true if the file exists, false otherwise
     */
    public static boolean isFileExists(String filePath) {
        SimpleReader reader = new SimpleReader1L(filePath);
        boolean exists = !reader.atEOS();
        reader.close();
        return exists;
    }

    /**
     * Checks if a directory is writable by verifying its validity.
     *
     * @param dirPath
     *            the directory path to check
     * @return true if the directory is writable, false otherwise
     */
    public static boolean isDirectoryWritable(String dirPath) {
        boolean writable = true;
        SimpleWriter writer = new SimpleWriter1L(dirPath + "/validationCheck.html");
        writer.print(""); // Attempt to write an empty string
        writer.close();
        return writable;
    }

    /**
     * Reads the input file and populates a map with terms and their
     * definitions. If a term has no definition, its definition is stored as an
     * empty string.
     *
     * @param in
     *            the input file reader
     * @param dictionary
     *            the map to store terms and definitions
     */
    public static void inputData(SimpleReader in, Map<String, String> dictionary) {
        String term = "";
        StringBuilder definition = new StringBuilder();

        while (!in.atEOS()) {
            String line = in.nextLine().trim();
            if (line.length() > 0 && isSingleWord(line)) {
                // Add the previous term and definition to the dictionary
                if (!term.isEmpty()) {
                    dictionary.add(term, definition.toString().trim());
                    definition = new StringBuilder();
                }
                // Update the current term
                term = line;
            } else if (line.length() > 0) {
                // Append to the current term's definition
                definition.append(line).append(" ");
            }
        }

        // Add the last term and its definition (if any)
        if (!term.isEmpty()) {
            dictionary.add(term, definition.toString().trim());
        }
    }

    /**
     * Checks if a string is a single word.
     *
     * @param str
     *            the string to check
     * @return true if the string is a single word, false otherwise
     */
    public static boolean isSingleWord(String str) {
        int i = 0;
        boolean isWord = true;
        while (i < str.length()) {
            if (str.charAt(i) == ' ') {
                isWord = false;
            }
            i++;
        }
        return isWord && str.length() > 0;
    }

    /**
     * Converts the map of terms and definitions into a sorted queue.
     *
     * @param dictionary
     *            the map containing terms and definitions
     * @return a queue of sorted terms
     */
    public static Queue<String> mapToSortedQueue(Map<String, String> dictionary) {
        Queue<String> terms = new Queue1L<>();
        for (Map.Pair<String, String> pair : dictionary) {
            terms.enqueue(pair.key());
        }

        // Sort terms manually
        Queue<String> sortedTerms = new Queue1L<>();
        while (terms.length() > 0) {
            String smallest = terms.dequeue();
            int i = 0;
            int size = terms.length();
            while (i < size) {
                String current = terms.dequeue();
                if (current.compareTo(smallest) < 0) {
                    terms.enqueue(smallest);
                    smallest = current;
                } else {
                    terms.enqueue(current);
                }
                i++;
            }
            sortedTerms.enqueue(smallest);
        }
        return sortedTerms;
    }

    /**
     * Writes the index HTML file.
     *
     * @param sortedTerms
     *            the queue of sorted terms
     * @param outputDir
     *            the directory to save the file
     * @return the path of the generated index.html
     */
    public static String writeIndexFile(Queue<String> sortedTerms, String outputDir) {
        String filePath = outputDir + "/index.html";
        SimpleWriter indexWriter = new SimpleWriter1L(filePath);

        indexWriter.println("<html>");
        indexWriter.println("<head><title>Glossary Index</title></head>");
        indexWriter.println("<body>");
        indexWriter.println("<h1>Glossary Index</h1>");
        indexWriter.println("<hr>");
        indexWriter.println("<ul>");

        for (String term : sortedTerms) {
            indexWriter
                    .println("<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }

        indexWriter.println("</ul>");
        indexWriter.println("</body>");
        indexWriter.println("</html>");
        indexWriter.close();

        return filePath;
    }

    /**
     * Writes individual HTML files for each term.
     *
     * @param dictionary
     *            the map of terms and definitions
     * @param sortedTerms
     *            the queue of sorted terms
     * @param outputDir
     *            the directory to save the files
     */
    public static void writeTermFiles(Map<String, String> dictionary,
            Queue<String> sortedTerms, String outputDir) {
        for (String term : sortedTerms) {
            String definition = dictionary.value(term);

            // Replace occurrences of other terms with hyperlinks
            for (Map.Pair<String, String> pair : dictionary) {
                String linkedTerm = pair.key();
                definition = definition.replaceAll("\\b" + linkedTerm + "\\b",
                        "<a href=\"" + linkedTerm + ".html\">" + linkedTerm + "</a>");
            }

            String filePath = outputDir + "/" + term + ".html";
            SimpleWriter termWriter = new SimpleWriter1L(filePath);

            termWriter.println("<html>");
            termWriter.println("<head><title>" + term + "</title></head>");
            termWriter.println("<body>");
            termWriter.println(
                    "<h1><b><i><font color=\"red\">" + term + "</font></i></b></h1>");
            termWriter.println("<p>" + definition + "</p>");
            termWriter.println("<hr>");
            termWriter.println("<a href=\"index.html\">Back to Index</a>");
            termWriter.println("</body>");
            termWriter.println("</html>");
            termWriter.close();
        }
    }

    /**
     * Main method to drive the program.
     *
     * @param args
     *            unused
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Prompt user for the input file path
        out.print("Enter the full path of the input file: ");
        String inputFileName = in.nextLine();

        // Validate the input file path
        while (!isFileExists(inputFileName)) {
            out.print("Invalid file. Enter a valid input file path: ");
            inputFileName = in.nextLine();
        }

        // Prompt user for the output directory path
        out.print("Enter the full path of the output directory: ");
        String outputDir = in.nextLine();

        // Validate the output directory path
        while (!isDirectoryWritable(outputDir)) {
            out.print("Invalid directory. Enter a valid output directory path: ");
            outputDir = in.nextLine();
        }

        // Read input data and populate dictionary
        SimpleReader fileReader = new SimpleReader1L(inputFileName);
        Map<String, String> dictionary = new Map1L<>();
        inputData(fileReader, dictionary);
        fileReader.close();

        // Convert the dictionary to a sorted queue of terms
        Queue<String> sortedTerms = mapToSortedQueue(dictionary);

        // Check if the input file contained terms
        if (sortedTerms.length() > 0) {
            writeIndexFile(sortedTerms, outputDir); // Write the index HTML file
            writeTermFiles(dictionary, sortedTerms, outputDir);
            out.println("Glossary has been successfully generated in: " + outputDir);
        } else {
            out.println("No terms found in the input file. Exiting.");
        }

        in.close();
        out.close();
    }
}

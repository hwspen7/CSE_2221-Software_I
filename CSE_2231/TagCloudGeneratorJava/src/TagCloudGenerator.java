import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Generates an HTML tag cloud using a user input file.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 */
public final class TagCloudGenerator {

    /**
     * Private constructor to prevent instantiation.
     */
    private TagCloudGenerator() {
    }

    /**
     * Comparator to sort by decreasing Integer values (frequencies), breaking
     * ties using ascending alphabetical order of the words.
     */
    private static final class IntegerSort
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
            int result = e2.getValue().compareTo(e1.getValue());
            if (result == 0) {
                result = e1.getKey().compareToIgnoreCase(e2.getKey());
            }
            return result;
        }
    }

    /**
     * Comparator to sort by alphabetical order of words, breaking ties using
     * descending frequency values.
     */
    private static final class StringSort
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
            int result = e1.getKey().compareToIgnoreCase(e2.getKey());
            if (result == 0) {
                result = e2.getValue().compareTo(e1.getValue());
            }
            return result;
        }
    }

    /**
     * The list of word-separating characters.
     */
    private static final String SEPARATORS = " .,:;'{][}|/><?!`~1234567890@#$%^&*()-_=+";

    /**
     * Creates a set of separator characters from the given string, including
     * the double-quote character {@code '"'}.
     *
     * @param str
     *            the string of separator characters
     * @return a set containing all characters in {@code str} and {@code '"'}
     */
    private static Set<Character> generateSetOfSeparators(String str) {
        Set<Character> separators = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            separators.add(str.charAt(i));
        }
        separators.add('"');
        return separators;
    }

    /**
     * Extracts the next word or separator substring starting at the given
     * position.
     *
     * @param text
     *            the input text
     * @param position
     *            the starting index
     * @param separators
     *            the set of separator characters
     * @return the next word or separator substring
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        boolean isSeparator = separators.contains(text.charAt(position));
        int end = position + 1;
        while (end < text.length()
                && separators.contains(text.charAt(end)) == isSeparator) {
            end++;
        }
        return text.substring(position, end);
    }

    /**
     * Fills the map with word counts from input.
     *
     * @param countMap
     *            the map to store word counts
     * @param separators
     *            the set of separator characters
     * @param input
     *            the input reader (BufferedReader)
     * @throws IOException
     *             if an I/O error occurs
     */
    private static void fillMap(Map<String, Integer> countMap, Set<Character> separators,
            BufferedReader input) throws IOException {
        assert countMap != null : "countMap is null";
        assert separators != null : "separators is null";
        assert input != null : "input is null";

        countMap.clear();
        String line;
        while ((line = input.readLine()) != null) {
            line = line.toLowerCase();
            int pos = 0;
            while (pos < line.length()) {
                String token = nextWordOrSeparator(line, pos, separators);
                if (!separators.contains(token.charAt(0))) {
                    countMap.put(token, countMap.getOrDefault(token, 0) + 1);
                }
                pos += token.length();
            }
        }
    }

    /**
     * Determines the CSS font class based on frequency.
     *
     * @param max
     *            maximum count
     * @param min
     *            minimum count
     * @param count
     *            word's count
     * @return corresponding CSS class string
     */
    private static String wordFontSize(int max, int min, int count) {
        final int maxFont = 48;
        final int minFont = 11;
        if (max == min) {
            return "f" + ((maxFont + minFont) / 2);
        }
        int font = (count - min) * (maxFont - minFont) / (max - min) + minFont;
        return "f" + font;
    }

    /**
     * Sorts the map entries and outputs the top N words to HTML. First sorts
     * entries by count (descending), selects the top N, then sorts those
     * alphabetically and prints them with font scaling.
     *
     * @param output
     *            the HTML writer
     * @param n
     *            number of top words to show
     * @param countMap
     *            map of words and their counts
     */
    private static void doubleSort(PrintWriter output, int n,
            Map<String, Integer> countMap) {

        // Step 1: Convert the map entries to a list
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            entryList.add(entry);
        }

        // Step 2: Sort the list in descending order by count (then alphabetically)
        Comparator<Map.Entry<String, Integer>> frequencyOrder = new IntegerSort();
        entryList.sort(frequencyOrder);

        // Step 3: Prepare the list of top N entries
        List<Map.Entry<String, Integer>> topEntries = new ArrayList<>();
        int index = 0;
        while (index < entryList.size() && index < n) {
            topEntries.add(entryList.get(index));
            index++;
        }

        // Step 4: Determine max and min counts among top entries
        int maxCount = 0;
        int minCount = 0;
        if (topEntries.size() > 0) {
            maxCount = topEntries.get(0).getValue(); // highest count
            minCount = topEntries.get(topEntries.size() - 1).getValue(); // lowest count
        }

        // Step 5: Sort the top entries alphabetically (by key)
        Comparator<Map.Entry<String, Integer>> alphabeticalOrder = new StringSort();
        topEntries.sort(alphabeticalOrder);

        // Step 6: Output each entry as an HTML span tag with scaled font size
        for (Map.Entry<String, Integer> entry : topEntries) {
            String word = entry.getKey();
            int count = entry.getValue();

            String cssClass = wordFontSize(maxCount, minCount, count);

            output.println("<span style=\"cursor:default\" class=\"" + cssClass
                    + "\" title=\"count: " + count + "\">" + word + "</span>");
        }
    }

    /**
     * Outputs opening tags for the HTML file.
     *
     * @param output
     *            output stream
     * @param inputFile
     *            name of the input file to read from
     * @param n
     *            number of words with highest counts
     * @updates output
     * @requires output is open and inputFile is not null
     * @ensures output content = #output content * tags
     */
    private static void outputHeader(PrintWriter output, String inputFile, int n) {
        assert output != null : "Violation of: output is not null.";
        assert inputFile != null : "Violation of: inputFile is not null.";

        output.println("<html>");
        output.println("<head>");
        output.println("<title>Top " + n + " words in " + inputFile + "</title>");
        output.println(
                "<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println("</head>");
        output.println("<body>");
        output.println("<h2>Top " + n + " words in " + inputFile + "</h2>");
        output.println("<hr>");
        output.println("<div class=\"cdiv\">");
        output.println("<p class=\"cbox\">");
    }

    /**
     * Outputs the HTML footer section.
     *
     * @param output
     *            the PrintWriter used to write to the HTML file
     * @updates output
     * @requires output is open
     * @ensures output content = #output content * HTML closing tags
     */
    private static void outputFooter(PrintWriter output) {
        assert output != null : "Violation of: output is not null.";
        output.println("</p></div></body></html>");
    }

    /**
     * Generates the full tag cloud output to the HTML file.
     *
     * @param output
     *            the HTML writer
     * @param input
     *            the text input reader
     * @param n
     *            the number of top words to include in the tag cloud
     * @throws IOException
     *             if an I/O error occurs
     */
    private static void outputTagCloud(PrintWriter output, BufferedReader input, int n)
            throws IOException {

        // Create map to store word counts
        Map<String, Integer> countMap = new HashMap<>();

        // Create set of separator characters
        Set<Character> separators = generateSetOfSeparators(SEPARATORS);

        // Fill map with word frequencies from input
        fillMap(countMap, separators, input);

        // Sort and output top N words to HTML
        doubleSort(output, n, countMap);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        try (BufferedReader console = new BufferedReader(
                new InputStreamReader(System.in));) {
            // Input file setup
            String inputFileName = "";
            BufferedReader inputFileReader = null;
            while (inputFileReader == null) {
                System.out.print("Enter the input file name: ");
                inputFileName = console.readLine();
                try {
                    inputFileReader = new BufferedReader(new FileReader(inputFileName));
                } catch (IOException e) {
                    System.out.println("Unable to open input file: " + e.getMessage());
                }
            }

            // Output file setup
            String outputFileName = "";
            PrintWriter outputWriter = null;
            while (outputWriter == null) {
                System.out.print("Enter the output file name: ");
                outputFileName = console.readLine();
                try {
                    outputWriter = new PrintWriter(
                            new BufferedWriter(new FileWriter(outputFileName)));
                } catch (IOException e) {
                    System.out.println("Unable to open output file: " + e.getMessage());
                }
            }

            // Number of words
            int n = 0;
            while (n <= 0) {
                System.out.print("Enter the number of words to be in the tag cloud"
                        + " (positive integer): ");
                try {
                    n = Integer.parseInt(console.readLine());
                    if (n <= 0) {
                        System.out.println("Number must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format.");
                }
            }

            // Process and output
            outputHeader(outputWriter, inputFileName, n);
            outputTagCloud(outputWriter, inputFileReader, n);
            outputFooter(outputWriter);

            // Clean up
            inputFileReader.close();
            outputWriter.close();
            System.out.println("Tag cloud successfully written to: " + outputFileName);

        } catch (IOException e) {
            System.err.println("Fatal error during processing: " + e.getMessage());
        }
    }
}

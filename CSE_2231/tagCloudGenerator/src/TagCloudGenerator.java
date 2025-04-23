import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;
import components.utilities.Reporter;

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
     * Comparator to sort by decreasing Integer values (frequencies).
     */
    private static final class IntegerSort implements Comparator<Pair<String, Integer>> {
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            int result = o2.value().compareTo(o1.value());
            if (result == 0) {
                result = o1.key().compareTo(o2.key());
            }
            return result;
        }
    }

    /**
     * Comparator to sort by alphabetical order of String keys (words).
     */
    private static final class StringSort implements Comparator<Pair<String, Integer>> {
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            int result = o1.key().compareTo(o2.key());
            if (result == 0) {
                result = o2.value().compareTo(o1.value());
            }
            return result;
        }
    }

    /**
     * The list of word-separating characters.
     */
    private static final String SEPARATORS = ". ,:;'{][}|/><?!`~1234567890@#$%^&*()-_=+";

    /**
     * Generates a Set of separator characters from a String.
     *
     * @param str
     *            the String containing separators
     * @return Set of characters including those in {@code str} and {@code '"'}
     */
    private static Set<Character> generateSetOfSeparators(String str) {
        Set<Character> separators = new Set1L<>();
        for (int i = 0; i < str.length(); i++) {
            separators.add(str.charAt(i));
        }
        separators.add('"');
        return separators;
    }

    /**
     * Returns the next word or separator substring from a given position.
     *
     * @param text
     *            the text line
     * @param position
     *            current position in text
     * @param separators
     *            the set of separator characters
     * @return next word or separator substring
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
     *            the input reader
     */
    private static void fillMap(Map<String, Integer> countMap, Set<Character> separators,
            SimpleReader input) {
        // Clear any existing entries in the map
        countMap.clear();
        // Extract words or separators from the line
        while (!input.atEOS()) {
            String line = input.nextLine().toLowerCase();
            int pos = 0;
            while (pos < line.length()) {
                String token = nextWordOrSeparator(line, pos, separators);
                // If token is a word (not a separator), count it
                if (!separators.contains(token.charAt(0))) {
                    if (countMap.hasKey(token)) {
                        countMap.replaceValue(token, countMap.value(token) + 1);
                    } else {
                        countMap.add(token, 1);
                    }
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
     * Sorts the map entries and outputs the top N words to HTML.
     *
     * @param output
     *            HTML writer
     * @param n
     *            number of top words to show
     * @param countMap
     *            map of words and counts
     */
    private static void doubleSort(SimpleWriter output, int n,
            Map<String, Integer> countMap) {
        // Sort map entries by frequency (high to low)
        Comparator<Pair<String, Integer>> countOrder = new IntegerSort();
        SortingMachine<Pair<String, Integer>> countSort = new SortingMachine2<>(
                countOrder);

        while (countMap.size() > 0) {
            countSort.add(countMap.removeAny());
        }
        countSort.changeToExtractionMode();
        // Prepare to sort top N by alphabetical order
        Comparator<Pair<String, Integer>> alphaOrder = new StringSort();
        SortingMachine<Pair<String, Integer>> alphaSort = new SortingMachine2<>(
                alphaOrder);

        int max = 0;
        int min = 0;
        int added = 0;
        // Output top N words in alphabetical order with scaled font size
        while (countSort.size() > 0 && added < n) {
            Pair<String, Integer> pair = countSort.removeFirst();
            if (added == 0) {
                max = pair.value();
            }
            alphaSort.add(pair);
            min = pair.value();
            added++;
        }

        alphaSort.changeToExtractionMode();
        while (alphaSort.size() > 0) {
            Pair<String, Integer> pair = alphaSort.removeFirst();
            String cssClass = wordFontSize(max, min, pair.value());
            output.println("<span style=\"cursor:default\" class=\"" + cssClass
                    + "\" title=\"count: " + pair.value() + "\">" + pair.key()
                    + "</span>");
        }
    }

    /**
     * Outputs the HTML header section.
     *
     * @param output
     *            HTML writer
     * @param inputFile
     *            input file name
     * @param n
     *            number of words
     */
    private static void outputHeader(SimpleWriter output, String inputFile, int n) {
        output.println(
                "<html><head><title>Top " + n + " words in " + inputFile + "</title>");
        output.println(
                "<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println("</head><body><h2>Top " + n + " words in " + inputFile + "</h2>");
        output.println("<hr>");
        output.println("<div class=\"cdiv\">");
        output.println("<p class=\"cbox\">");
    }

    /**
     * Outputs the HTML footer section.
     *
     * @param output
     *            HTML writer
     */
    private static void outputFooter(SimpleWriter output) {
        output.println("</p></div></body></html>");
    }

    /**
     * Generates the full tag cloud output to the HTML file.
     *
     * @param output
     *            HTML writer
     * @param input
     *            input reader
     * @param n
     *            number of words
     */
    private static void outputTagCloud(SimpleWriter output, SimpleReader input, int n) {
        // Create map to store word counts
        Map<String, Integer> countMap = new Map1L<>();
        // Create set of separator characters
        Set<Character> separators = generateSetOfSeparators(SEPARATORS);
        // Fill map with word frequencies from input
        fillMap(countMap, separators, input);
        // Sort and output top N words to HTML
        doubleSort(output, n, countMap);
    }

    /**
     * Main method: reads user input, processes text, writes tag cloud.
     *
     * @param args
     *            command-line arguments (not used)
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the input file name: ");
        String inputFile = in.nextLine();

        out.print("Enter the output file name: ");
        String outputFile = in.nextLine();

        // Prompt the user to enter how many top words to include in the tag cloud
        out.print("Enter the number of words to include in the tag cloud: ");
        int n = in.nextInteger();
        // Ensure the number of words is positive; otherwise, stop the program
        Reporter.assertElseFatalError(n > 0, "Number of words must be positive.");

        SimpleReader fileInput = new SimpleReader1L(inputFile);
        SimpleWriter fileOutput = new SimpleWriter1L(outputFile);

        outputHeader(fileOutput, inputFile, n);
        outputTagCloud(fileOutput, fileInput, n);
        outputFooter(fileOutput);

        in.close();
        out.close();
        fileInput.close();
        fileOutput.close();
    }

}

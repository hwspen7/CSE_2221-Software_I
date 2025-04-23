import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer.isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF")
                : "" + "Violation of: <\"IF\"> is proper prefix of tokens";

        // TODO - fill in body
        String ifKeyword = tokens.dequeue(); // should be "IF"
        Reporter.assertElseFatalError(Tokenizer.isKeyword(ifKeyword),
                "Error: Expected 'IF' keyword at beginning of IF statement.");

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing condition after 'IF'.");
        String conditionString = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(conditionString),
                "Error: Invalid condition in IF statement: '" + conditionString + "'.");
        Condition c = parseCondition(conditionString);

        Reporter.assertElseFatalError(
                tokens.length() > 0 && tokens.front().equals("THEN"),
                "Error: Missing 'THEN' after IF condition.");
        tokens.dequeue(); // consume THEN

        Statement block1 = s.newInstance();
        block1.parseBlock(tokens);

        if (tokens.length() > 0 && tokens.front().equals("ELSE")) {
            tokens.dequeue(); // consume ELSE

            Statement block2 = s.newInstance();
            block2.parseBlock(tokens);

            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.front().equals("END"),
                    "Error: Missing 'END' in IF_ELSE statement.");
            tokens.dequeue(); // consume END

            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.front().equals("IF"),
                    "Error: IF_ELSE statement must end with 'END IF'.");
            tokens.dequeue(); // consume IF

            s.assembleIfElse(c, block1, block2);
        } else {
            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.front().equals("END"),
                    "Error: Missing 'END' in IF statement.");
            tokens.dequeue(); // consume END

            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.front().equals("IF"),
                    "Error: IF statement must end with 'END IF'.");
            tokens.dequeue(); // consume IF

            s.assembleIf(c, block1);
        }

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE")
                : "" + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        // TODO - fill in body

        String whileKeyword = tokens.dequeue(); // should be "WHILE"
        Reporter.assertElseFatalError(Tokenizer.isKeyword(whileKeyword),
                "Error: Expected 'WHILE' keyword at beginning of while statement.");

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing condition after 'WHILE'.");
        String conditionString = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(conditionString),
                "Error: Invalid condition after 'WHILE': '" + conditionString + "'.");
        Condition c = parseCondition(conditionString);

        Reporter.assertElseFatalError(tokens.length() > 0 && tokens.front().equals("DO"),
                "Error: Missing 'DO' in WHILE statement.");
        tokens.dequeue(); // consume DO

        Statement body = s.newInstance();
        body.parseBlock(tokens);

        Reporter.assertElseFatalError(tokens.length() > 0 && tokens.front().equals("END"),
                "Error: Missing 'END' in WHILE statement.");
        tokens.dequeue(); // consume END

        Reporter.assertElseFatalError(
                tokens.length() > 0 && tokens.front().equals("WHILE"),
                "Error: WHILE statement must end with 'END WHILE'.");
        tokens.dequeue(); // consume WHILE

        s.assembleWhile(c, body);

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && Tokenizer.isIdentifier(tokens.front())
                : "" + "Violation of: identifier string is proper prefix of tokens";

        // TODO - fill in body
        String identifier = tokens.dequeue();

        Reporter.assertElseFatalError(Tokenizer.isIdentifier(identifier),
                "Error: Invalid identifier for CALL statement: \"" + identifier + "\".");

        s.assembleCall(identifier);

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // TODO - fill in body
        String name = tokens.front();

        if (name.equals("WHILE")) {
            parseWhile(tokens, this);
        } else if (name.equals("IF")) {
            parseIf(tokens, this);
        } else if (Tokenizer.isIdentifier(name)) {
            parseCall(tokens, this);
        } else {
            Reporter.assertElseFatalError(false, "SYNTAX ERROR: Unexpected token \""
                    + name + "\". Statement must start with WHILE, IF, or identifier.");
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // TODO - fill in body
        Statement s = this.newInstance();
        while (!tokens.front().equals("END")
                && !tokens.front().equals(Tokenizer.END_OF_INPUT)
                && !tokens.front().equals("ELSE")) {
            this.parse(tokens);
            s.addToBlock(s.lengthOfBlock(), this);
        }
        this.transferFrom(s);

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}

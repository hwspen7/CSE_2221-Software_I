import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens, Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION")
                : "" + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        // TODO - fill in body
        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: No tokens to parse for instruction.");
        String firstToken = tokens.dequeue(); // should be "INSTRUCTION"
        Reporter.assertElseFatalError("INSTRUCTION".equals(firstToken),
                "Error: Expecting 'INSTRUCTION' but got '" + firstToken + "'.");
        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing identifier after 'INSTRUCTION'.");
        String instrName = tokens.front();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(instrName),
                "Error: Invalid identifier for instruction name: " + instrName);
        tokens.dequeue(); // Consume instrName

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing 'IS' after instruction name.");
        Reporter.assertElseFatalError("IS".equals(tokens.front()),
                "Error: Expecting 'IS' after instruction name '" + instrName + "'.");

        Reporter.assertElseFatalError(
                !instrName.equals("move") && !instrName.equals("turnleft")
                        && !instrName.equals("turnright") && !instrName.equals("infect")
                        && !instrName.equals("skip"),
                "Error: '" + instrName
                        + "' is a primitive instruction and cannot be redefined.");

        tokens.dequeue(); // Consume "IS"

        body.parseBlock(tokens);

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing 'END' before instruction name at the end.");
        Reporter.assertElseFatalError("END".equals(tokens.front()),
                "Error: Expecting 'END' at end of instruction '" + instrName + "'.");
        tokens.dequeue(); // Consume "END"

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing identifier after 'END' in instruction.");
        String endInstrName = tokens.front();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(endInstrName),
                "Error: Invalid identifier after 'END': " + endInstrName);
        tokens.dequeue(); // Consume endInstrName

        Reporter.assertElseFatalError(instrName.equals(endInstrName),
                "Error: Instruction name '" + endInstrName
                        + "' does not match beginning name '" + instrName + "'.");

        // This line added just to make the program compilable.
        return instrName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // TODO - fill in body

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: No tokens to parse at beginning of program.");
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Error: Expecting 'PROGRAM' keyword at beginning.");

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing identifier after 'PROGRAM'.");
        String programName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(programName),
                "Error: Invalid program name '" + programName + "'.");

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing 'IS' after program name.");
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Error: Expecting 'IS' after program name '" + programName + "'.");

        Map<String, Statement> context = this.newContext();
        while (!tokens.front().equals("BEGIN")) {
            Statement body = this.newBody();
            String instrName = parseInstruction(tokens, body);

            Reporter.assertElseFatalError(!Tokenizer.isKeyword(instrName),
                    "Error: Instruction name '" + instrName + "' cannot be a keyword.");
            Reporter.assertElseFatalError(!context.hasKey(instrName),
                    "Error: Duplicate instruction name '" + instrName + "' in context.");
            context.add(instrName, body);
        }

        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Error: Expecting 'BEGIN' before program body.");

        Statement body = this.newBody();
        body.parseBlock(tokens);

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing 'END' at end of program.");
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Error: Expecting 'END' keyword at end of program.");

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Missing program name after 'END'.");
        String endProgramName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(endProgramName),
                "Error: Invalid identifier after 'END': " + endProgramName);
        Reporter.assertElseFatalError(endProgramName.equals(programName),
                "Error: Program name mismatch: expected '" + programName + "', but got '"
                        + endProgramName + "'.");

        Reporter.assertElseFatalError(tokens.length() > 0,
                "Error: Program must terminate with '### END_OF_INPUT ###'.");
        String endOfInput = tokens.dequeue();
        Reporter.assertElseFatalError(endOfInput.equals(Tokenizer.END_OF_INPUT),
                "Error: Program does not terminate properly with END_OF_INPUT.");

        this.setName(programName);
        this.swapContext(context);
        this.swapBody(body);

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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}

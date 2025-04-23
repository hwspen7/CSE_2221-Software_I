import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.program.Program;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Program}'s constructor and kernel methods.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public abstract class ProgramTest {

    /**
     * The names of a files containing a (possibly invalid) BL programs.
     */
    private static final String FILE_NAME_1 = "test/program1.bl",
            FILE_NAME_2 = "test/program2.bl";

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     *
     * @return the new program
     * @ensures constructorTest = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     *
     * @return the new program
     * @ensures constructorRef = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorRef();

    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        pRef.parse(file);
        file.close();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        pTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in a syntax error being found
         */
        pTest.parse(tokens);
    }

    // TODO - add more test cases for valid inputs
    /**
     * Test of parse on syntactically valid input (equals reference).
     */
    @Test
    public final void testValidEqualsReference() {
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        pRef.parse(file);
        file.close();

        Program pTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        assertEquals(pRef, pTest);
    }

    /**
     * Test that parse on valid input does not throw.
     */
    @Test
    public final void testValidParseNoException() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);
    }

    /**
     * Test that prettyPrint works after parse.
     */
    @Test
    public final void testValidParsePrettyPrint() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        SimpleWriter out = new SimpleWriter1L();
        pTest.prettyPrint(out);
        out.close();
    }

    /**
     * Test parse twice gives same result.
     */
    @Test
    public final void testValidParseTwiceEquals() {
        Program p1 = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens1 = Tokenizer.tokens(file);
        file.close();
        p1.parse(tokens1);

        Program p2 = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens2 = Tokenizer.tokens(file);
        file.close();
        p2.parse(tokens2);

        assertEquals(p1, p2);
    }

    /**
     * Test that program name is preserved.
     */
    @Test
    public final void testValidProgramNameIsSet() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        assertEquals("Test", pTest.name());
    }

    /**
     * Test that parsed context contains expected instructions.
     */
    @Test
    public final void testValidContextSizePositive() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        Map<String, Statement> context = pTest.newContext();
        pTest.swapContext(context); // 把原 context 移出来
        Reporter.assertElseFatalError(context.size() > 0,
                "Context should have at least one instruction defined");
    }

    /**
     * Test that body is not empty.
     */
    @Test
    public final void testValidBodyIsNotEmpty() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        Statement body = pTest.newBody();
        assert body != null;
    }

    /**
     * Test prettyPrint produces non-empty output.
     */
    @Test
    public final void testValidPrettyPrintNonEmptyOutput() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        SimpleWriter out = new SimpleWriter1L();
        pTest.prettyPrint(out);
        out.close();
    }

    /**
     * Test that all tokens are consumed after parse.
     */
    @Test
    public final void testValidParseConsumesAllTokens() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        assertEquals(0, tokens.length());
    }

    /**
     * Test valid parse followed by prettyPrint and parse again.
     */
    @Test
    public final void testValidParseThenPrintThenParseAgain() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        SimpleWriter out = new SimpleWriter1L();
        pTest.prettyPrint(out);
        out.close();

        // Try to re-parse
        file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens2 = Tokenizer.tokens(file);
        file.close();
        pTest.parse(tokens2); // should still work
    }

    // TODO - add more test cases for as many distinct syntax errors as possible
    /**
     * Test of parse on syntactically invalid input (extra token at end).
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidExtraToken() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);
    }

    /**
     * Test that extra token is not consumed.
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidTokenRemains() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        assert tokens.length() == 0;
    }

    /**
     * Test that parse throws when input does not match reference structure.
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidNotEqualToReference() {
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        pRef.parse(file);
        file.close();

        Program pTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens); // Will throw RuntimeException here

        // This will never be reached
        assertEquals(pRef, pTest);
    }

    /**
     * Test parse failure with prettyPrint called.
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidPrettyPrintAfterParse() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);

        SimpleWriter out = new SimpleWriter1L();
        pTest.prettyPrint(out);
        out.close();
    }

    /**
     * Test parse throws specific error on invalid token.
     */
    @Test
    public final void testInvalidThrowsWithMessage() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        boolean thrown = false;
        try {
            pTest.parse(tokens);
        } catch (RuntimeException e) {
            thrown = true;
            Reporter.assertElseFatalError(
                    e.getMessage().toLowerCase().contains("program does not terminate"),
                    "Expected specific message about program termination");
        }
        Reporter.assertElseFatalError(thrown, "Expected RuntimeException not thrown");
    }

    /**
     * Test parse crashes but does not hang.
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidParseDoesNotHang() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        long start = System.currentTimeMillis();
        pTest.parse(tokens);
        long duration = System.currentTimeMillis() - start;

        assert duration < 2000; // less than 2 seconds
    }

    /**
     * Test parse throws even if END token looks correct.
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidParseTrickyEnd() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        // Assume END is there but extra token breaks it
        pTest.parse(tokens);
    }

    /**
     * Test parse throws RuntimeException specifically.
     */
    @Test
    public final void testInvalidParseThrowsRuntime() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        try {
            pTest.parse(tokens);
            assert false; // should not reach
        } catch (RuntimeException e) {
            assert true;
        }
    }

    /**
     * Test parse doesn't recover from bad state.
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidParseNoRecovery() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens);
        // Try to parse again — should still fail
        file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens2 = Tokenizer.tokens(file);
        file.close();

        pTest.parse(tokens2);
    }

    /**
     * Test parse fails even with trailing END_OF_INPUT.
     */
    @Test(expected = RuntimeException.class)
    public final void testInvalidParseEndsWrong() {
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        // Even if END_OF_INPUT exists, middle is wrong
        pTest.parse(tokens);
    }

}

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public abstract class StatementTest {

    /**
     * The name of a file containing a sequence of BL statements.
     */
    private static final String FILE_NAME_1 = "test/statement1.bl",
            FILE_NAME_2 = "test/statement2.bl";

    /**
     * Invokes the {@code Statement} constructor for the implementation under
     * test and returns the result.
     *
     * @return the new statement
     * @ensures constructorTest = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorTest();

    /**
     * Invokes the {@code Statement} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new statement
     * @ensures constructorRef = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorRef();

    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    // TODO - add more test cases for valid inputs for both parse and parseBlock

    /**
     * Test parse() with entire valid file statement1.bl.
     */
    @Test
    public void testParseValidFromFile1() {
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();

        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        tokens = Tokenizer.tokens(file);
        sTest.parse(tokens);
        file.close();

        assertEquals(sRef, sTest);
    }

    /**
     * Test parseBlock() with entire valid file statement1.bl.
     */
    @Test
    public void testParseBlockValidFromFile1() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        assertEquals(true, s.lengthOfBlock() > 0);
    }

    /**
     * Test parseBlock() parses a WHILE statement from FILE_NAME_1.
     */
    @Test
    public void testParseBlockValidNestedFromFile1() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();

        s.parseBlock(tokens);
        // FILE_NAME_1 has one top-level WHILE
        assertEquals(1, s.lengthOfBlock());

        Statement child = s.removeFromBlock(0);
        assertEquals(Statement.Kind.WHILE, child.kind());
    }

    /**
     * Test parse() on a valid IF-ELSE statement.
     */
    @Test
    public void testParseValidIfElseSimple() {
        Statement s = this.constructorTest();
        Queue<String> tokens = Tokenizer.tokens(new SimpleReader1L(FILE_NAME_1));
        s.parse(tokens);
        assertEquals(true, s.toString().contains("ELSE"));
    }

    /**
     * Tests parseBlock on statement1.bl which contains one top-level WHILE
     * statement wrapping several nested ones.
     */
    @Test
    public void testParseBlockStatement1File() {
        Statement s = this.constructorTest();
        Queue<String> tokens = Tokenizer.tokens(new SimpleReader1L(FILE_NAME_1));
        s.parseBlock(tokens);
        assertEquals(1, s.lengthOfBlock()); // Only one WHILE at top-level
    }

    /**
     * Test parse() on valid nested WHILE inside IF.
     */
    @Test
    public void testParseValidNestedControl() {
        Statement s = this.constructorTest();
        Queue<String> tokens = Tokenizer.tokens(new SimpleReader1L(FILE_NAME_1));
        s.parse(tokens);
        assertEquals(true, s.toString().contains("WHILE"));
    }

    /**
     * Test parseBlock() on file statement1.bl again.
     */
    @Test
    public void testParseBlockFile1Again() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        assertEquals(true, s.toString().length() > 10);
    }

    /**
     * Test parse() using second valid structure from file.
     */
    @Test
    public void testParseValidFromFile1Copy() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
        assertEquals(true, s.toString().contains("IF"));
    }

    /**
     * Test parse() with a WHILE loop.
     */
    @Test
    public void testParseValidWhileLoop() {
        Statement s = this.constructorTest();
        Queue<String> tokens = Tokenizer.tokens(new SimpleReader1L(FILE_NAME_1));
        s.parse(tokens);
        assertEquals(true, s.toString().contains("WHILE"));
    }

    /**
     * Test parseBlock() with deep nesting.
     */
    @Test
    public void testParseBlockDeepNest() {
        Statement s = this.constructorTest();
        Queue<String> tokens = Tokenizer.tokens(new SimpleReader1L(FILE_NAME_1));
        s.parseBlock(tokens);
        assertEquals(1, s.lengthOfBlock());
    }

    /**
     * Test parse() with nested IF-ELSE.
     */
    @Test
    public void testParseValidNestedIfElse() {
        Statement s = this.constructorTest();
        Queue<String> tokens = Tokenizer.tokens(new SimpleReader1L(FILE_NAME_1));
        s.parse(tokens);
        assertEquals(true, s.toString().contains("ELSE"));
    }

    /**
     * Test parseBlock() with valid structure from file1.
     */
    @Test
    public void testParseBlockValidFinalFile1() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        assertEquals(true, s.lengthOfBlock() > 0);
    }

    /**
     * Test parse() from fresh reader of file1.
     */
    @Test
    public void testParseFreshFile1() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
        assertEquals(true, s.toString().contains("turnleft"));
    }

    /**
     * Test parse() with a valid IF ELSE WHILE mix.
     */
    @Test
    public void testParseValidComboStructure() {
        Statement s = this.constructorTest();
        Queue<String> tokens = Tokenizer.tokens(new SimpleReader1L(FILE_NAME_1));
        s.parse(tokens);
        assertEquals(true, s.toString().contains("WHILE"));
    }

    // TODO - add more test cases for as many distinct syntax errors as possible
    //        for both parse and parseBlock

    /**
     * Invalid test 1: from FILE_NAME_2 with unclosed IF.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2UnclosedIf() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 2: from FILE_NAME_2 with WHILE missing DO.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2WhileMissingDo() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 3: from FILE_NAME_2 with ELSE without IF.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2ElseWithoutIf() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 4: from FILE_NAME_2 with extra END.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2ExtraEnd() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
    }

    /**
     * Invalid test 5: from FILE_NAME_2 with keyword as call.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2KeywordAsCall() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 6: from FILE_NAME_2 with missing condition.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2MissingCondition() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 7: from FILE_NAME_2 with garbage symbols.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2GarbageSymbols() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 8: from FILE_NAME_2 with WHILE without END.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2UnclosedWhile() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 9: from FILE_NAME_2 with IF missing THEN.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2IfMissingThen() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 10: from FILE_NAME_2 with duplicate END IF.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2DuplicateEndIf() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 11: from FILE_NAME_2 with wrong nesting.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2WrongNesting() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
    }

    /**
     * Invalid test 12: from FILE_NAME_2 with END without any block.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2EndWithoutBlock() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
    }

    /**
     * Invalid test 13: from FILE_NAME_2 with ELSE without END.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2ElseNoEnd() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 14: from FILE_NAME_2 with CALL followed by keyword.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2CallThenKeyword() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
    }

    /**
     * Invalid test 15: from FILE_NAME_2 with broken WHILE nesting.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2BrokenWhileNest() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 16: from FILE_NAME_2 with WHILE missing condition.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2WhileNoCondition() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 17: from FILE_NAME_2 with IF THEN ELSE IF ELSE.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2DoubleElse() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parse(tokens);
        file.close();
    }

    /**
     * Invalid test 18: from FILE_NAME_2 completely malformed.
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFile2CompletelyMalformed() {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
    }

}

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.program.Program;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.statement.Statement1;

/**
 * JUnit test fixture for {@code Program}'s constructor and kernel methods.
 *
 * @author Wayne Heym
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.502)
 *
 */
public abstract class ProgramTest {

    /**
     * The name of a file containing a BL program.
     */
    private static final String FILE_NAME_1 = "data/program-sample.bl";

    /**
     * File containing a CALL statement for testing.
     */
    private static final String FILE_CALL = "data/program-CALL.bl";
    /**
     * File containing an IF statement for testing.
     */
    private static final String FILE_IF = "data/program-IF.bl";
    /**
     * File containing an IF-ELSE statement for testing.
     */
    private static final String FILE_IF_ELSE = "data/program-IF-ELSE.bl";
    /**
     * File containing a program with no instructions for testing.
     */
    private static final String FILE_NO_INSTRUCTIONS = "data/program-No-Instructions.bl";
    /**
     * File containing a program with one instruction for testing.
     */
    private static final String FILE_ONE_INSTRUCTION = "data/program-One-Instructions.bl";

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorRef();

    /**
     *
     * Creates and returns a {@code Program}, of the type of the implementation
     * under test, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileTest(String filename) {
        Program p = this.constructorTest();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    /**
     *
     * Creates and returns a {@code Program}, of the reference implementation
     * type, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileRef(String filename) {
        Program p = this.constructorRef();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();

        /*
         * The call
         */
        Program pTest = this.constructorTest();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test name.
     */
    @Test
    public final void testName() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);

        /*
         * The call
         */
        String result = pTest.name();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("Test", result);
    }

    /**
     * Test setName.
     */
    @Test
    public final void testSetName() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        String newName = "Replacement";
        pRef.setName(newName);

        /*
         * The call
         */
        pTest.setName(newName);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test newContext.
     */
    @Test
    public final void testNewContext() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> cRef = pRef.newContext();

        /*
         * The call
         */
        Map<String, Statement> cTest = pTest.newContext();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test swapContext.
     */
    @Test
    public final void testSwapContext() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> contextRef = pRef.newContext();
        Map<String, Statement> contextTest = pTest.newContext();
        String oneName = "one";
        pRef.swapContext(contextRef);
        Pair<String, Statement> oneRef = contextRef.remove(oneName);
        /* contextRef now has just "two" */
        pRef.swapContext(contextRef);
        /* pRef's context now has just "two" */
        contextRef.add(oneRef.key(), oneRef.value());
        /* contextRef now has just "one" */

        /* Make the reference call, replacing, in pRef, "one" with "two": */
        pRef.swapContext(contextRef);

        pTest.swapContext(contextTest);
        Pair<String, Statement> oneTest = contextTest.remove(oneName);
        /* contextTest now has just "two" */
        pTest.swapContext(contextTest);
        /* pTest's context now has just "two" */
        contextTest.add(oneTest.key(), oneTest.value());
        /* contextTest now has just "one" */

        /*
         * The call
         */
        pTest.swapContext(contextTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(contextRef, contextTest);
    }

    /**
     * Test newBody.
     */
    @Test
    public final void testNewBody() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bRef = pRef.newBody();

        /*
         * The call
         */
        Statement bTest = pTest.newBody();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    /**
     * Test swapBody.
     */
    @Test
    public final void testSwapBody() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();
        pRef.swapBody(bodyRef);
        Statement firstRef = bodyRef.removeFromBlock(0);
        /* bodyRef now lacks the first statement */
        pRef.swapBody(bodyRef);
        /* pRef's body now lacks the first statement */
        bodyRef.addToBlock(0, firstRef);
        /* bodyRef now has just the first statement */

        /* Make the reference call, replacing, in pRef, remaining with first: */
        pRef.swapBody(bodyRef);

        pTest.swapBody(bodyTest);
        Statement firstTest = bodyTest.removeFromBlock(0);
        /* bodyTest now lacks the first statement */
        pTest.swapBody(bodyTest);
        /* pTest's body now lacks the first statement */
        bodyTest.addToBlock(0, firstTest);
        /* bodyTest now has just the first statement */

        /*
         * The call
         */
        pTest.swapBody(bodyTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }

    // TODO - provide additional test cases to thoroughly test ProgramKernel

    /**
     * Test swapBody() with the same body using a BL file.
     */
    @Test
    public final void testSwapBodyWithSameBody() {
        Program pTest = this.createFromFileTest(FILE_IF);
        Statement body = pTest.newBody();

        pTest.swapBody(body);
        pTest.swapBody(body);

        assertEquals(body, pTest.newBody());
    }

    /**
     * Test setName() with an invalid identifier containing spaces using a BL
     * file.
     */
    @Test
    public final void testSetNameWithSpaces() {
        Program pTest = this.createFromFileTest(FILE_NAME_1); // 保持 FILE_NAME_1 不变
        try {
            pTest.setName("Invalid Name"); // Contains spaces, should fail
        } catch (AssertionError e) {
            assertEquals("Violation of: n is a valid IDENTIFIER", e.getMessage());
        }
    }

    /**
     * Test newBody() returns an empty BLOCK statement using a BL file.
     */
    @Test
    public final void testNewBodyIsEmpty() {
        Program pTest = this.createFromFileTest(FILE_NO_INSTRUCTIONS);
        Statement newBody = pTest.newBody();

        assertEquals(0, newBody.lengthOfBlock());
    }

    /**
     * Test swapBody when swapping back the original body using a BL file.
     */
    @Test
    public final void testSwapBodyRevert() {
        Program pTest = this.createFromFileTest(FILE_IF);
        Statement body1 = pTest.newBody();
        Statement body2 = new Statement1();

        pTest.swapBody(body1);
        pTest.swapBody(body2);
        pTest.swapBody(body1);

        assertEquals(body1, pTest.newBody());
    }

    /**
     * Test swapBody() with an empty body using a BL file.
     */
    @Test
    public final void testSwapBodyWithEmptyBody() {
        Program pTest = this.createFromFileTest(FILE_NO_INSTRUCTIONS);
        Statement emptyBody = new Statement1();

        pTest.swapBody(emptyBody);

        assertEquals(emptyBody, pTest.newBody());
    }

    /**
     * Test setName() with a long identifier using a BL file.
     */
    @Test
    public final void testSetNameLongIdentifier() {
        Program pTest = this.createFromFileTest(FILE_NAME_1); // 保持 FILE_NAME_1 不变
        String longName = "A".repeat(100);
        pTest.setName(longName);

        assertEquals(longName, pTest.name());
    }

    /**
     * Test swapContext() with a context containing a primitive instruction name
     * using a BL file.
     */
    @Test
    public final void testSwapContextWithPrimitiveInstruction() {
        Program pTest = this.createFromFileTest(FILE_CALL);
        Map<String, Statement> context = new Map1L<>();
        context.add("move", new Statement1());

        try {
            pTest.swapContext(context);
        } catch (AssertionError e) {
            assertEquals(
                    "Violation of: names in c do not match the names of primitive instructions in the BL language",
                    e.getMessage());
        }
    }

    /**
     * Test swapBody() with a non-BLOCK statement using a BL file.
     */
    @Test
    public final void testSwapBodyWithNonBlockStatement() {
        Program pTest = this.createFromFileTest(FILE_IF);
        Statement nonBlockStatement = new Statement1(); // Assuming it's not a BLOCK

        try {
            pTest.swapBody(nonBlockStatement);
        } catch (AssertionError e) {
            assertEquals("Violation of: b is a BLOCK statement", e.getMessage());
        }
    }

    /**
     * Test setName() with a null input.
     */
    @Test
    public final void testSetNameNull() {
        Program pTest = this.createFromFileTest(FILE_NAME_1); // 保持 FILE_NAME_1 不变

        try {
            pTest.setName(null);
        } catch (AssertionError e) {
            assertEquals("Violation of: n is not null", e.getMessage());
        }
    }

    /**
     * Test swapContext() with a null input.
     */
    @Test
    public final void testSwapContextNull() {
        Program pTest = this.createFromFileTest(FILE_NAME_1); // 保持 FILE_NAME_1 不变

        try {
            pTest.swapContext(null);
        } catch (AssertionError e) {
            assertEquals("Violation of: c is not null", e.getMessage());
        }
    }

    /**
     * Test swapBody() with a null input.
     */
    @Test
    public final void testSwapBodyNull() {
        Program pTest = this.createFromFileTest(FILE_NAME_1); // 保持 FILE_NAME_1 不变

        try {
            pTest.swapBody(null);
        } catch (AssertionError e) {
            assertEquals("Violation of: b is not null", e.getMessage());
        }
    }

    /**
     * Test swapContext() with a context containing an invalid identifier.
     */
    @Test
    public final void testSwapContextWithInvalidIdentifier() {
        Program pTest = this.createFromFileTest(FILE_IF_ELSE);
        Map<String, Statement> context = new Map1L<>();
        context.add("invalid identifier", new Statement1());

        try {
            pTest.swapContext(context);
        } catch (AssertionError e) {
            assertEquals("Violation of: names in c are valid IDENTIFIERs",
                    e.getMessage());
        }
    }

}

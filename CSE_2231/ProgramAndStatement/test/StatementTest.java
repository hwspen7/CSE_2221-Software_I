import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;
import components.statement.StatementKernel.Kind;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Wayne Heym
 * @author Spencer Qin(qin.709) and Yuxuan Wan(wan.709)
 *
 */
public abstract class StatementTest {

    /**
     * The name of a file containing a sequence of BL statements.
     */
    private static final String FILE_NAME_1 = "data/statement-sample.bl";
 
    /**
     * File containing an empty BL statement for testing.
     */
    private static final String FILE_NAME_EMPTY = "data/statement-sampleEmpty.bl";
    /**
     * File containing a WHILE statement for testing.
     */
    private static final String FILE_NAME_WHILE = "data/statement-sampleWHILE.bl";

    /**
     * Invokes the {@code Statement} constructor for the implementation under
     * test and returns the result.
     *
     * @return the new statement
     * @ensures constructor = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorTest();

    /**
     * Invokes the {@code Statement} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new statement
     * @ensures constructor = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorRef();

    /**
     *
     * Creates and returns a block {@code Statement}, of the type of the
     * implementation under test, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed for the sequence of
     *            statements to go in the block statement
     * @return the constructed block statement
     * @ensures <pre>
     * createFromFile = [the block statement containing the statements
     * parsed from the file]
     * </pre>
     */
    private Statement createFromFileTest(String filename) {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(filename);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        return s;
    }

    /**
     *
     * Creates and returns a block {@code Statement}, of the reference
     * implementation type, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed for the sequence of
     *            statements to go in the block statement
     * @return the constructed block statement
     * @ensures <pre>
     * createFromFile = [the block statement containing the statements
     * parsed from the file]
     * </pre>
     */
    private Statement createFromFileRef(String filename) {
        Statement s = this.constructorRef();
        SimpleReader file = new SimpleReader1L(filename);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        return s;
    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();

        /*
         * The call
         */
        Statement sTest = this.constructorTest();

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test kind of a WHILE statement.
     */
    @Test
    public final void testKindWhile() {
        /*
         * Setup
         */
        final int whilePos = 3;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_1);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = sourceTest.removeFromBlock(whilePos);
        Statement sRef = sourceRef.removeFromBlock(whilePos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test addToBlock at an interior position.
     */
    @Test
    public final void testAddToBlockInterior() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = sRef.newInstance();
        Statement nestedTest = sTest.removeFromBlock(1);
        Statement nestedRef = sRef.removeFromBlock(1);
        sRef.addToBlock(2, nestedRef);

        /*
         * The call
         */
        sTest.addToBlock(2, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test removeFromBlock at the front leaving a non-empty block behind.
     */
    @Test
    public final void testRemoveFromBlockFrontLeavingNonEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement nestedRef = sRef.removeFromBlock(0);

        /*
         * The call
         */
        Statement nestedTest = sTest.removeFromBlock(0);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nestedRef, nestedTest);
    }

    /**
     * Test lengthOfBlock, greater than zero.
     */
    @Test
    public final void testLengthOfBlockNonEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        int lengthRef = sRef.lengthOfBlock();

        /*
         * The call
         */
        int lengthTest = sTest.lengthOfBlock();

        /*
         * Evaluation
         */
        assertEquals(lengthRef, lengthTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test assembleIf.
     */
    @Test
    public final void testAssembleIf() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sourceTest.newInstance();
        Condition c = sourceTest.disassembleIf(nestedTest);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIf(c, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleIf.
     */
    @Test
    public final void testDisassembleIf() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIf(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIf(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test assembleIfElse.
     */
    @Test
    public final void testAssembleIfElse() {
        /*
         * Setup
         */
        final int ifElsePos = 2;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(ifElsePos);
        Statement sRef = blockRef.removeFromBlock(ifElsePos);
        Statement thenBlockTest = sourceTest.newInstance();
        Statement elseBlockTest = sourceTest.newInstance();
        Condition cTest = sourceTest.disassembleIfElse(thenBlockTest, elseBlockTest);
        Statement sTest = blockTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIfElse(cTest, thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, thenBlockTest);
        assertEquals(emptyBlock, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleIfElse.
     */
    @Test
    public final void testDisassembleIfElse() {
        /*
         * Setup
         */
        final int ifElsePos = 2;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(ifElsePos);
        Statement sRef = blockRef.removeFromBlock(ifElsePos);
        Statement thenBlockTest = sTest.newInstance();
        Statement elseBlockTest = sTest.newInstance();
        Statement thenBlockRef = sRef.newInstance();
        Statement elseBlockRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIfElse(thenBlockRef, elseBlockRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIfElse(thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(cRef, cTest);
        assertEquals(thenBlockRef, thenBlockTest);
        assertEquals(elseBlockRef, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test assembleWhile.
     */
    @Test
    public final void testAssembleWhile() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(1);
        Statement sourceRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sourceTest.newInstance();
        Statement nestedRef = sourceRef.newInstance();
        Condition cTest = sourceTest.disassembleIf(nestedTest);
        Condition cRef = sourceRef.disassembleIf(nestedRef);
        Statement sRef = sourceRef.newInstance();
        sRef.assembleWhile(cRef, nestedRef);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleWhile(cTest, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleWhile.
     */
    @Test
    public final void testDisassembleWhile() {
        /*
         * Setup
         */
        final int whilePos = 3;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(whilePos);
        Statement sRef = blockRef.removeFromBlock(whilePos);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleWhile(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleWhile(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test assembleCall.
     */
    @Test
    public final void testAssembleCall() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef().newInstance();
        Statement sTest = this.constructorTest().newInstance();

        String name = "look-for-something";
        sRef.assembleCall(name);

        /*
         * The call
         */
        sTest.assembleCall(name);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleCall.
     */
    @Test
    public final void testDisassembleCall() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(0);
        Statement sRef = blockRef.removeFromBlock(0);
        String nRef = sRef.disassembleCall();

        /*
         * The call
         */
        String nTest = sTest.disassembleCall();

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nRef, nTest);
    }

    // TODO - provide additional test cases to thoroughly test StatementKernel
    /**
     * Test addToBlock at the beginning (pos = 0).
     */
    @Test
    public final void testAddToBlockBeginning() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement nestedTest = sTest.removeFromBlock(1);
        Statement nestedRef = sRef.removeFromBlock(1);
        sRef.addToBlock(0, nestedRef);

        /*
         * The call
         */
        sTest.addToBlock(0, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test addToBlock at the end (pos = lengthOfBlock).
     */
    @Test
    public final void testAddToBlockEnd() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement nestedTest = sTest.removeFromBlock(1);
        Statement nestedRef = sRef.removeFromBlock(1);
        int pos = sRef.lengthOfBlock();
        sRef.addToBlock(pos, nestedRef);

        /*
         * The call
         */
        sTest.addToBlock(pos, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test removeFromBlock when only one statement remains.
     */
    @Test
    public final void testRemoveFromBlockLastStatement() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        while (sRef.lengthOfBlock() > 1) {
            sRef.removeFromBlock(0);
            sTest.removeFromBlock(0);
        }

        /*
         * The call
         */
        Statement lastRemovedRef = sRef.removeFromBlock(0);
        Statement lastRemovedTest = sTest.removeFromBlock(0);

        /*
         * Evaluation
         */
        assertEquals(lastRemovedRef, lastRemovedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test lengthOfBlock when empty.
     */
    @Test
    public final void testLengthOfBlockEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        Statement sRef = this.constructorRef();

        /*
         * The call
         */
        int lengthTest = sTest.lengthOfBlock();
        int lengthRef = sRef.lengthOfBlock();

        /*
         * Evaluation
         */
        assertEquals(0, lengthTest);
        assertEquals(0, lengthRef);
    }

    /**
     * Test assembleIfElse with different conditions.
     */
    @Test
    public final void testAssembleIfElseDifferentConditions() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement thenBlockTest = blockTest.newInstance();
        Statement elseBlockTest = blockTest.newInstance();
        Statement thenBlockRef = blockRef.newInstance();
        Statement elseBlockRef = blockRef.newInstance();
        Condition c1 = Condition.NEXT_IS_EMPTY;
        Condition c2 = Condition.NEXT_IS_NOT_EMPTY;

        /*
         * The call
         */
        Statement sTest1 = blockTest.newInstance();
        Statement sRef1 = blockRef.newInstance();
        sTest1.assembleIfElse(c1, thenBlockTest, elseBlockTest);
        sRef1.assembleIfElse(c1, thenBlockRef, elseBlockRef);

        Statement sTest2 = blockTest.newInstance();
        Statement sRef2 = blockRef.newInstance();
        sTest2.assembleIfElse(c2, thenBlockTest, elseBlockTest);
        sRef2.assembleIfElse(c2, thenBlockRef, elseBlockRef);

        /*
         * Evaluation
         */
        assertEquals(sRef1, sTest1);
        assertEquals(sRef2, sTest2);
    }

    /**
     * Test assembleWhile with different conditions.
     */
    @Test
    public final void testAssembleWhileDifferentConditions() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement loopBlockTest = blockTest.newInstance();
        Statement loopBlockRef = blockRef.newInstance();
        Condition c1 = Condition.NEXT_IS_EMPTY;
        Condition c2 = Condition.NEXT_IS_NOT_EMPTY;

        /*
         * The call
         */
        Statement sTest1 = blockTest.newInstance();
        Statement sRef1 = blockRef.newInstance();
        sTest1.assembleWhile(c1, loopBlockTest);
        sRef1.assembleWhile(c1, loopBlockRef);

        Statement sTest2 = blockTest.newInstance();
        Statement sRef2 = blockRef.newInstance();
        sTest2.assembleWhile(c2, loopBlockTest);
        sRef2.assembleWhile(c2, loopBlockRef);

        /*
         * Evaluation
         */
        assertEquals(sRef1, sTest1);
        assertEquals(sRef2, sTest2);
    }

    /**
     * Test disassembleCall and check instruction name.
     */
    @Test
    public final void testDisassembleCallInstructionName() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        Statement sRef = this.constructorRef();
        String name = "execute-action";
        sTest.assembleCall(name);
        sRef.assembleCall(name);

        /*
         * The call
         */
        String instTest = sTest.disassembleCall();
        String instRef = sRef.disassembleCall();

        /*
         * Evaluation
         */
        assertEquals(instRef, instTest);
        assertEquals(name, instTest);
    }

    /**
     * Test lengthOfBlock on an empty block statement.
     */
    @Test
    public final void testLengthOfBlockEmptyStatement() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_EMPTY);
        Statement sRef = this.createFromFileRef(FILE_NAME_EMPTY);

        /*
         * The call
         */
        int lengthTest = sTest.lengthOfBlock();
        int lengthRef = sRef.lengthOfBlock();

        /*
         * Evaluation
         */
        assertEquals(0, lengthTest);
        assertEquals(0, lengthRef);
    }

    /**
     * Test assembleWhile and disassembleWhile using WHILE statement.
     */
    @Test
    public final void testAssembleAndDisassembleWhile() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_WHILE);
        Statement blockRef = this.createFromFileRef(FILE_NAME_WHILE);
        Statement loopBlockTest = blockTest.newInstance();
        Statement loopBlockRef = blockRef.newInstance();
        Condition cTest = Condition.NEXT_IS_EMPTY;
        Condition cRef = Condition.NEXT_IS_EMPTY;

        /*
         * The call
         */
        Statement sTest = blockTest.newInstance();
        Statement sRef = blockRef.newInstance();
        sTest.assembleWhile(cTest, loopBlockTest);
        sRef.assembleWhile(cRef, loopBlockRef);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);

        /*
         * Disassemble
         */
        Condition disassembledCTest = sTest.disassembleWhile(loopBlockTest);
        Condition disassembledCRef = sRef.disassembleWhile(loopBlockRef);

        /*
         * Evaluation
         */
        assertEquals(disassembledCRef, disassembledCTest);
        assertEquals(loopBlockRef, loopBlockTest);
        assertEquals(sRef, sTest);
    }

}

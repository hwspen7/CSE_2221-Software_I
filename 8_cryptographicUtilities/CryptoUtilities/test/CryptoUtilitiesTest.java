import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Tuo Qin
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    /**
     * Tests of reduceToGCD with inputs 0 and 0. The expected GCD is 0 and both
     * n and m should remain 0.
     */
    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of reduceToGCD with inputs 30 and 21. The expected GCD is 3 and m
     * should be reduced to 0.
     */
    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of reduceToGCD with inputs 13 and 39. The expected GCD is 13 and m
     * should be reduced to 0.
     */
    @Test
    public void testReduceToGCD_13_39() {
        NaturalNumber n = new NaturalNumber2(13);
        NaturalNumber nExpected = new NaturalNumber2(13);
        NaturalNumber m = new NaturalNumber2(39);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of reduceToGCD with inputs 48 and 18. The expected GCD is 6 and m
     * should be reduced to 0.
     */
    @Test
    public void testReduceToGCD_48_18() {
        NaturalNumber n = new NaturalNumber2(48);
        NaturalNumber nExpected = new NaturalNumber2(6);
        NaturalNumber m = new NaturalNumber2(18);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of reduceToGCD with inputs 101 and 10. The expected GCD is 1 and m
     * should be reduced to 0.
     */
    @Test
    public void testReduceToGCD_101_10() {
        NaturalNumber n = new NaturalNumber2(101);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber m = new NaturalNumber2(10);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of reduceToGCD with inputs 24 and 0. The expected GCD is 24 and m
     * should remain 0.
     */
    @Test
    public void testReduceToGCD_24_0() {
        NaturalNumber n = new NaturalNumber2(24);
        NaturalNumber nExpected = new NaturalNumber2(24);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of reduceToGCD with inputs 0 and 15. The expected GCD is 15 and n
     * should be reduced to 15.
     */
    @Test
    public void testReduceToGCD_0_15() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(15);
        NaturalNumber m = new NaturalNumber2(15);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    /**
     * Tests of isEven with input 0. The expected result is true, indicating
     * that 0 is even.
     */
    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /**
     * Tests of isEven with input 1. The expected result is false, indicating
     * that 1 is odd.
     */
    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /**
     * Tests of isEven with input 2. The expected result is true, indicating
     * that 2 is even.
     */
    @Test
    public void testIsEven_2() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /**
     * Tests of isEven with input 3. The expected result is false, indicating
     * that 3 is odd.
     */
    @Test
    public void testIsEven_3() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber nExpected = new NaturalNumber2(3);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /**
     * Tests of isEven with input 4. The expected result is true, indicating
     * that 4 is even.
     */
    @Test
    public void testIsEven_4() {
        NaturalNumber n = new NaturalNumber2(4);
        NaturalNumber nExpected = new NaturalNumber2(4);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /**
     * Tests of isEven with a large even number. The input is 1000000, which is
     * expected to return true, and n should remain unchanged.
     */
    @Test
    public void testIsEven_largeEven() {
        NaturalNumber n = new NaturalNumber2(1000000);
        NaturalNumber nExpected = new NaturalNumber2(1000000);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /**
     * Tests of isEven with a large odd number. The input is 1000001, which is
     * expected to return false, and n should remain unchanged.
     */
    @Test
    public void testIsEven_largeOdd() {
        NaturalNumber n = new NaturalNumber2(1000001);
        NaturalNumber nExpected = new NaturalNumber2(1000001);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */

    /**
     * Tests of powerMod with inputs 0, 0, and 2. The expected result of 0^0 mod
     * 2 is 1, and n and m should remain unchanged.
     */
    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of powerMod with inputs 17, 18, and 19. The expected result of
     * 17^18 mod 19 is 1, and p and m should remain unchanged.
     */
    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of powerMod with inputs 5, 3, and 13. The expected result of 5^3
     * mod 13 is 8, and p and m should remain unchanged.
     */
    @Test
    public void testPowerMod_5_3_13() {
        NaturalNumber n = new NaturalNumber2(5);
        NaturalNumber nExpected = new NaturalNumber2(8);
        NaturalNumber p = new NaturalNumber2(3);
        NaturalNumber pExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(13);
        NaturalNumber mExpected = new NaturalNumber2(13);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of powerMod with inputs 2, 10, and 1000. The expected result of
     * 2^10 mod 1000 is 24, and p and m should remain unchanged.
     */
    @Test
    public void testPowerMod_2_10_1000() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(24);
        NaturalNumber p = new NaturalNumber2(10);
        NaturalNumber pExpected = new NaturalNumber2(10);
        NaturalNumber m = new NaturalNumber2(1000);
        NaturalNumber mExpected = new NaturalNumber2(1000);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of powerMod with inputs 3, 5, and 7. The expected result of 3^5 mod
     * 7 is 5, and p and m should remain unchanged.
     */
    @Test
    public void testPowerMod_3_5_7() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber nExpected = new NaturalNumber2(5);
        NaturalNumber p = new NaturalNumber2(5);
        NaturalNumber pExpected = new NaturalNumber2(5);
        NaturalNumber m = new NaturalNumber2(7);
        NaturalNumber mExpected = new NaturalNumber2(7);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of powerMod with inputs 1, 1, and 2. The expected result of 1^1 mod
     * 2 is 1, and n, p, and m should remain unchanged.
     */
    @Test
    public void testPowerMod_1_1_2() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(1);
        NaturalNumber pExpected = new NaturalNumber2(1);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Tests of powerMod with inputs 7, 0, and 5. The expected result of 7^0 mod
     * 5 is 1, and p and m should remain unchanged.
     */
    @Test
    public void testPowerMod_7_0_5() {
        NaturalNumber n = new NaturalNumber2(7);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(5);
        NaturalNumber mExpected = new NaturalNumber2(5);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isWitnessToCompositeness
     */

    /**
     * Tests of isWitnessToCompositeness with inputs 4 and 2. The number 2 is a
     * witness to the compositeness of 4, so the assertion should be true.
     */
    @Test
    public void compositeWitness_4_2() {
        NaturalNumber n = new NaturalNumber2(4);
        NaturalNumber w = new NaturalNumber2(2);
        assertTrue(CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /**
     * Tests of isWitnessToCompositeness with inputs 7 and 2. The number 2 is
     * not a witness to the compositeness of 7, so the assertion should be
     * false.
     */
    @Test
    public void compositeWitness_7_2() {
        NaturalNumber n = new NaturalNumber2(7);
        NaturalNumber w = new NaturalNumber2(2);
        assertTrue(!CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /**
     * Tests of isWitnessToCompositeness with inputs 100 and 30. The number 30
     * is a witness to the compositeness of 100, so the assertion should be
     * true.
     */
    @Test
    public void Compositeness_100_30() {
        NaturalNumber n = new NaturalNumber2(100);
        NaturalNumber w = new NaturalNumber2(30);
        assertTrue(CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /**
     * Tests of isWitnessToCompositeness with inputs 243 and 31. The number 31
     * is a witness to the compositeness of 243, so the assertion should be
     * true.
     */
    @Test
    public void Compositeness_243_31() {
        NaturalNumber n = new NaturalNumber2(243);
        NaturalNumber w = new NaturalNumber2(31);
        assertTrue(CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /**
     * Tests of isWitnessToCompositeness with inputs 134535 and 341. The number
     * 341 is a witness to the compositeness of 134535, so the assertion should
     * be true.
     */
    @Test
    public void Compositeness_134535_341() {
        NaturalNumber n = new NaturalNumber2(134535);
        NaturalNumber w = new NaturalNumber2(341);
        assertTrue(CryptoUtilities.isWitnessToCompositeness(w, n));
    }

    /*
     * Tests of isPrime1
     */

    /**
     * Tests of isPrime1 with input 2. Since 2 is a prime number, the assertion
     * should be true.
     */
    @Test
    public void isPrime1_2() {
        NaturalNumber n = new NaturalNumber2(2);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime1 with input 3. Since 3 is a prime number, the assertion
     * should be true.
     */
    @Test
    public void isPrime1_3() {
        NaturalNumber n = new NaturalNumber2(3);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime1 with input 15. Since 15 is not a prime number, the
     * assertion should be false.
     */
    @Test
    public void isPrime1_15() {
        NaturalNumber n = new NaturalNumber2(15);
        assertTrue(!CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime1 with input 99. Since 99 is not a prime number, the
     * assertion should be false.
     */
    @Test
    public void isPrime1_99() {
        NaturalNumber n = new NaturalNumber2(99);
        assertTrue(!CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime1 with a large prime number, Integer.MAX_VALUE.
     * Integer.MAX_VALUE is a prime number, so the assertion should be true.
     */
    @Test
    public void isPrime1_big() {
        NaturalNumber n = new NaturalNumber2(Integer.MAX_VALUE);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /*
     * Tests of isPrime2
     */

    /**
     * Tests of isPrime2 with input 2. Since 2 is a prime number, the assertion
     * should be true.
     */
    @Test
    public void isPrime2_2() {
        NaturalNumber n = new NaturalNumber2(2);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime2 with input 3. Since 3 is a prime number, the assertion
     * should be true.
     */
    @Test
    public void isPrime2_3() {
        NaturalNumber n = new NaturalNumber2(3);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime2 with input 15. Since 15 is not a prime number, the
     * assertion should be false.
     */
    @Test
    public void isPrime2_15() {
        NaturalNumber n = new NaturalNumber2(15);
        assertTrue(!CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime2 with input 99. Since 99 is not a prime number, the
     * assertion should be false.
     */
    @Test
    public void isPrime2_99() {
        NaturalNumber n = new NaturalNumber2(99);
        assertTrue(!CryptoUtilities.isPrime2(n));
    }

    /**
     * Tests of isPrime2 with a large prime number, Integer.MAX_VALUE.
     * Integer.MAX_VALUE is a prime number, so the assertion should be true.
     */
    @Test
    public void isPrime2_big() {
        NaturalNumber n = new NaturalNumber2(Integer.MAX_VALUE);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    /*
     * Tests of generateNextLikelyPrime
     */

    /**
     * Tests generateNextLikelyPrime with input 4. The next prime after 4 is 5,
     * so the expected output should be "5".
     */
    @Test
    public void testGenerateNextLikelyToPrime_4() {
        NaturalNumber n = new NaturalNumber2(4);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("5", n.toString());
    }

    /**
     * Tests generateNextLikelyPrime with input 8. The next prime after 8 is 11,
     * so the expected output should be "11".
     */
    @Test
    public void testGenerateNextLikelyToPrime_8() {
        NaturalNumber n = new NaturalNumber2(8);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("11", n.toString());
    }

    /**
     * Tests generateNextLikelyPrime with input 55. The next prime after 55 is
     * 59, so the expected output should be "59".
     */
    @Test
    public void testGenerateNextLikelyToPrime_55() {
        NaturalNumber n = new NaturalNumber2(55);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("59", n.toString());
    }

    /**
     * Tests generateNextLikelyPrime with a larger input, 32425345. The next
     * prime after 32425345 is 32425373, so the expected output should be
     * "32425373".
     */
    @Test
    public void testGenerateNextLikelyToPrime_32425345() {
        NaturalNumber n = new NaturalNumber2(32425345);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("32425373", n.toString());
    }

    /**
     * Tests generateNextLikelyPrime with a large input near Integer.MAX_VALUE.
     * For Integer.MAX_VALUE - 1, the next prime is Integer.MAX_VALUE
     * (2147483647).
     */
    @Test
    public void testGenerateNextLikelyToPrime_big() {
        NaturalNumber n = new NaturalNumber2(Integer.MAX_VALUE - 1);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("2147483647", n.toString());
    }
}

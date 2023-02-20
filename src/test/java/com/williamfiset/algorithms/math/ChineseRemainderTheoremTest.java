package com.williamfiset.algorithms.math;


import org.junit.Test;
import static org.junit.Assert.*;

/**
* The following class contains tests for the ChineseRemainderTheorem class.
 * The first three tests assure 100% branch coverage for function reduce().
 * The fourth test assure 100% branch coverage for function crt().
* */
public class ChineseRemainderTheoremTest {

    /**
    * Test that reduce correctly reduces a number of equations.
    * Assert that the result is not null (since the equations can be reduced to coprime)
    * Assert that a and m is reduced correctly
    * */
    @Test
    public void testReduce(){
        long[] a = {1, 2, 3, 4, 5};
        long[] m = {2, 3, 4, 5, 6};
        long[][] res = ChineseRemainderTheorem.reduce(a, m);
        assertNotNull(res);
        long[] aExpected = {3, 4, 2};
        long[] mExpected = {4, 5, 3};
        assertArrayEquals(res[0], aExpected);
        assertArrayEquals(res[1], mExpected);
    }

    /**
     * Test that reduce return null when the equations cannot be reduced due to a conflict.
     * Test for else on line 91
     * The equation x = 1 (mod 2), x = 0 (mod 2) cannot be solved
     * Assert that res is null
     * */
    @Test
    public void testNotReducibleConflict1(){
        long[] a1 = {1, 0};
        long[] m1 = {2, 2};
        long[][] res = ChineseRemainderTheorem.reduce(a1, m1);
        assertNull(res);
    }

    /**
     * Test that reduce return null when the equations cannot be reduced due to a conflict.
     * Test for else on line 84
     * The equation x = 0 (mod 4), x = 1 (mod 2) cannot be solved
     * Assert that res is null
     * */
    @Test
    public void testNotReducibleConflict2(){
        long[] a1 = {0, 1};
        long[] m1 = {4, 2};
        long[][] res = ChineseRemainderTheorem.reduce(a1, m1);
        assertNull(res);
    }

    /**
    * Test that CRT returns the correct solution to the equations
    * Assert that a and m from res is correct.
    * */
    @Test
    public void testCRT(){
        long[] a = {3, 4, 2};
        long[] m = {4, 5, 3};
        long[] res = ChineseRemainderTheorem.crt(a, m);
        assertEquals(res[0], 59);
        assertEquals(res[1], 60);
    }
}


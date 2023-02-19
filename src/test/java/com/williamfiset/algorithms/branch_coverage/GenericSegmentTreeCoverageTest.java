package com.williamfiset.algorithms.branch_coverage;

/** JUnit dependencies */
import org.junit.*;
import static org.junit.Assert.assertTrue;

/** Other utils */
import com.williamfiset.algorithms.branch_coverage.GenericSegmentTreeCoverage.RangeUpdateFn;
import com.williamfiset.algorithms.branch_coverage.GenericSegmentTreeCoverage.SegmentCombinationFn;

public class GenericSegmentTreeCoverageTest {
    /*
     * Tests the construction of a valid tree
     */
    @Test
    public void testValidTree() {
        try {
            long[] v = {1, 4, 3, 0, 5, 8, -2, 7, 5, 2, 9};
            GenericSegmentTreeCoverage st =
            new GenericSegmentTreeCoverage(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);
            st.printDebugInfo();
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    /*
     * Tests exception handling for 'null' values
     */
    @Test
    public void testNullValues() {
        boolean thrown = false;

        try {
            GenericSegmentTreeCoverage tree = new GenericSegmentTreeCoverage(null, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);
            System.out.println(tree);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    /*
     * Tests exception handling for 'null' segment combination function
     */
    @Test
    public void testNullSegmentCombination() {
        boolean thrown = false;

        try {
            long[] v = {1, 4, 3, 0, 5, 8, -2, 7, 5, 2, 9};
            GenericSegmentTreeCoverage tree = new GenericSegmentTreeCoverage(v, null, RangeUpdateFn.ASSIGN);
            System.out.println(tree);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    /*
     * Tests exception handling for 'null' range update function
     */
    @Test
    public void testNullRangeUpdateFcn() {
        boolean thrown = false;

        try {
            long[] v = {1, 4, 3, 0, 5, 8, -2, 7, 5, 2, 9};
            GenericSegmentTreeCoverage tree = new GenericSegmentTreeCoverage(v, SegmentCombinationFn.MIN, null);
            System.out.println(tree);
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
 
    /**
     * Print the branch coverage report, using an ad hoc tool, after all the unit tests have
     * been executed.
     */
    @AfterClass
    public static void printCoverageReport() {
        String report = GenericSegmentTreeCoverage.instrumentation.getReport();
        System.out.println(report);
    }
}

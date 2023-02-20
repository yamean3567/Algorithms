package com.williamfiset.algorithms.branch_coverage;

/** JUnit dependencies */
import org.junit.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


import com.williamfiset.algorithms.branch_coverage.ClosestPairOfPoints.PT;


public class ClosestPairOfPointsTest {
    /**
     * Test wether an empty array is returned if number of points is less than two.
     */
    @Test
    public void testNoPair() {

        ClosestPairOfPoints p = new ClosestPairOfPoints();

        PT[] points = new PT[]{
            p.new PT(1,1)
        };

        ClosestPairOfPoints.PT[] result = ClosestPairOfPoints.closestPair(points);
        PT[] expected = new PT[]{};
        assertArrayEquals("One point can create a pare, should return empty array",expected, result);
    }

    /**
     * Test wether an array of four points returns the correct pair of points closest to each other.
     * The points are (0,0),(1,1),(4,4),(10,10) and should return the pair (0,0),(1,1)
     */
    @Test
    public void testCorrectPair() {

        ClosestPairOfPoints p = new ClosestPairOfPoints();

        PT[] points = new PT[]{
            p.new PT(0,0),
            p.new PT(1,1),
            p.new PT(4,4),
            p.new PT(10,10)
        };

        PT[] expected = new PT[]{
            p.new PT(1,1),
            p.new PT(0,0)
        };

        PT[] result = ClosestPairOfPoints.closestPair(points);
        assertEquals("Expects x-coordinate 1 of first point",result[0].x, expected[0].x, 10e-6);
        assertEquals("Expects y-coordinate 1 of first point", result[0].y, expected[0].y, 10e-6);
        assertEquals("Expects x-coordinate 0 of second point", result[1].x, expected[1].x, 10e-6);
        assertEquals("Expects x-coordinate 0 of second point", result[1].y, expected[1].y, 10e-6);
    }

    /**
     * Test wether a correct pair of points are returned if all the points can create
     * a pair with equal distance.
     */
    @Test
    public void testAllPointsPairEqualDistance() {

        ClosestPairOfPoints p = new ClosestPairOfPoints();

        PT[] points = new PT[]{
            p.new PT(1, 1), 
            p.new PT(2, 2), 
            p.new PT(3, 3), 
            p.new PT(4, 4), 
            p.new PT(5, 5), 
            p.new PT(6, 6)
        };

        PT[] expected = new PT[]{
            p.new PT(2,2),
            p.new PT(1,1)
        };

        PT[] result = ClosestPairOfPoints.closestPair(points);
        assertEquals("Expects x-coordinate 2 of first point",result[0].x, expected[0].x, 10e-6);
        assertEquals("Expects y-coordinate 2 of first point", result[0].y, expected[0].y, 10e-6);
        assertEquals("Expects x-coordinate 1 of second point", result[1].x, expected[1].x, 10e-6);
        assertEquals("Expects x-coordinate 1 of second point", result[1].y, expected[1].y, 10e-6);
    }

    /**
     * Test wether the function returns correct pair of points if all points are on the
     * negative y and x axis. 
     */
    @Test
    public void testNegativePoints() {

        ClosestPairOfPoints p = new ClosestPairOfPoints();

        PT[] points = new PT[]{
            p.new PT(-1, -1), 
            p.new PT(-4, -4), 
            p.new PT(-2, -10), 
            p.new PT(-1, -5)
        };

        PT[] expected = new PT[]{
            p.new PT(-1,-5),
            p.new PT(-4, -4),
        };

        PT[] result = ClosestPairOfPoints.closestPair(points);
        assertEquals("Expects x-coordinate 2 of first point",result[0].x, expected[0].x, 10e-6);
        assertEquals("Expects y-coordinate 2 of first point", result[0].y, expected[0].y, 10e-6);
        assertEquals("Expects x-coordinate 1 of second point", result[1].x, expected[1].x, 10e-6);
        assertEquals("Expects x-coordinate 1 of second point", result[1].y, expected[1].y, 10e-6);
    }
    /**
     * Test wether the function returns two points if all are points are identical.
     */
    @Test
    public void testAllSamePoints(){
        ClosestPairOfPoints p = new ClosestPairOfPoints();

        PT[] points = new PT[]{
            p.new PT(0,0),
            p.new PT(0,0),
            p.new PT(0,0),
            p.new PT(0,0)
        };
        PT[] expected = new PT[]{
            p.new PT(0,0),
            p.new PT(0,0),
        };
        PT[] result = ClosestPairOfPoints.closestPair(points);
        
        assertEquals("Expects x-coordinate 0 of first point",result[0].x, expected[0].x, 10e-6);
        assertEquals("Expects y-coordinate 0 of first point", result[0].y, expected[0].y, 10e-6);
        assertEquals("Expects x-coordinate 0 of second point", result[1].x, expected[1].x, 10e-6);
        assertEquals("Expects x-coordinate 0 of second point", result[1].y, expected[1].y, 10e-6);
    }

     /**
     * Print the branch coverage report, using an ad hoc tool, after all the unit tests have
     * been executed.
     */
    @AfterClass
    public static void printCoverageReport() {
        String report = ClosestPairOfPoints.instrumentation.getReport();
        System.out.println(report);
    }
}
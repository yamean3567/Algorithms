package com.williamfiset.algorithms.branch_coverage;

/** JUnit dependencies */
import org.junit.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/** Other utils */
import com.williamfiset.algorithms.branch_coverage.LineSegmentLineSegmentIntersectionCoverage.Pt;

public class LineSegmentLineSegmentIntersectionCoverageTest {
    /**
     * Test whether an empty array is returned when there is no intersection 
     * between the two line segments. 
     */
    @Test
    public void testNoIntersection() {
        Pt p1 = new Pt(0, 0);
        Pt p2 = new Pt(0, 1);
        Pt p3 = new Pt(1, 0);
        Pt p4 = new Pt(1, 1);

        Pt[] result = LineSegmentLineSegmentIntersectionCoverage.lineSegmentLineSegmentIntersection(p1, p2, p3, p4);

        assertArrayEquals("No intersection, should equal an empty array", new Pt[0], result);
    }

    /**
     * Tests a single point intersection. The segments form a T-shape.
     */
    @Test
    public void testGeneralSinglePointIntersection() {
        // Intersection occurs at (2,0)
        Pt p1 = new Pt(0, 0);
        Pt p2 = new Pt(3, 0);
        Pt p3 = new Pt(2, 0);
        Pt p4 = new Pt(2, -2);

        Pt[] result = LineSegmentLineSegmentIntersectionCoverage.lineSegmentLineSegmentIntersection(p1, p2, p3, p4);

        assertEquals("The intersection is one single point", result.length, 1);

        assertEquals("Intersection x-coordinate", 2, result[0].x, 10e-6);
        assertEquals("Intersection y-coordinate", 0, result[0].y, 10e-6);
    }

    /**
     * Test whether the same point is returned if all line segments are defined
     * by the same single point. 
     */
    @Test
    public void testSameSinglePointIntersection() {
        Pt p1 = new Pt(0, 0);
        Pt p2 = new Pt(0, 0);
        Pt p3 = new Pt(0, 0);
        Pt p4 = new Pt(0, 0);

        Pt[] result = LineSegmentLineSegmentIntersectionCoverage.lineSegmentLineSegmentIntersection(p1, p2, p3, p4);

        assertEquals("The intersection is one single point", result.length, 1);

        assertEquals("The intersection has the same x-coordinate as the other points", 0, result[0].x, 10e-6);
        assertEquals("The intersection has the same y-coordinate as the other points", 0, result[0].y, 10e-6);
    }

    /**
     * Test whether the intersection of two identical segments is the segment.
     */
    @Test
    public void testIdenticalSegments() {
        Pt p1 = new Pt(0, 0);
        Pt p2 = new Pt(0, 1);
        Pt p3 = new Pt(0, 0);
        Pt p4 = new Pt(0, 1);

        Pt[] result = LineSegmentLineSegmentIntersectionCoverage.lineSegmentLineSegmentIntersection(p1, p2, p3, p4);

        assertEquals("The intersection is two points", result.length, 2);

        assertEquals("First endpoints' x-coordinate", 0, result[0].x, 10e-6);
        assertEquals("First endpoints' y-coordinate", 0, result[0].y, 10e-6);

        assertEquals("Second endpoints' x-coordinate", 0, result[1].x, 10e-6);
        assertEquals("Second endpoints' y-coordinate", 1, result[1].y, 10e-6);
    }

    /**
     * Test whether the smaller of two co-linear segments is returned, given two 
     * unidentical linear segments.
     */
    @Test
    public void testUnidenticalColinearSegments() {
        // Intersection is the segment with endpoints p3 and p4.
        Pt p1 = new Pt(0, 0);
        Pt p2 = new Pt(6, 0);
        Pt p3 = new Pt(2, 0);
        Pt p4 = new Pt(4, 0);

        Pt[] result = LineSegmentLineSegmentIntersectionCoverage.lineSegmentLineSegmentIntersection(p1, p2, p3, p4);

        assertEquals("The intersection is two points", result.length, 2);

        assertEquals("First endpoints' x-coordinate", 2, result[0].x, 10e-6);
        assertEquals("First endpoints' y-coordinate", 0, result[0].y, 10e-6);

        assertEquals("Second endpoints' x-coordinate", 4, result[1].x, 10e-6);
        assertEquals("Second endpoints' y-coordinate", 0, result[1].y, 10e-6);
    }

    /**
     * Print the branch coverage report, using an ad hoc tool, after all the unit tests have
     * been executed.
     */
    @AfterClass
    public static void printCoverageReport() {
        String report = LineSegmentLineSegmentIntersectionCoverage.instrumentation.getReport();
        System.out.println(report);
    }
}

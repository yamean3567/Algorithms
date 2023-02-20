/**
 * This file contains code to find the closest pair of points given a list of points.
 *
 * <p>Time Complexity: O(nlog(n))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 * 
 * Modified to include branch coverage
 */
package com.williamfiset.algorithms.branch_coverage;

import static java.lang.Math.*;

import java.util.*;

public class ClosestPairOfPoints {

    private static final double EPS = 1e-9;
    
    public static AdHoc instrumentation = new AdHoc(12);

    public class PT {
        double x, y;

        public PT(double xx, double yy) {
        x = xx;
        y = yy;
        }

        public double dist(PT pt) {
        double dx = x - pt.x, dy = y - pt.y;
        return sqrt(dx * dx + dy * dy);
        }
    }

    // Sorts points by X coordinate
    private static class X_Sort implements Comparator<PT> {
        @Override
        public int compare(PT pt1, PT pt2) {
        if (abs(pt1.x - pt2.x) < EPS) return 0;
        return (pt1.x < pt2.x) ? -1 : +1;
        }
    }

    // Sorts points by Y coordinate first then X coordinate
    private static class YX_Sort implements Comparator<PT> {
        @Override
        public int compare(PT pt1, PT pt2) {
        if (abs(pt1.y - pt2.y) < EPS) {
            if (abs(pt1.x - pt2.x) < EPS) return 0;
            return (pt1.x < pt2.x) ? -1 : +1;
        }
        return (pt1.y < pt2.y) ? -1 : +1;
        }
    }

    // Finds the closest pair of points in a list of points
    public static PT[] closestPair(PT[] points) {

        int branchId = 0;

        if (points == null || points.length < 2){
            branchId = 0;
            instrumentation.coverBranch(branchId);
            return new PT[] {};
        } else {
            branchId = 1;
            instrumentation.coverBranch(branchId);
        }

        final int n = points.length;
        int xQueueFront = 0, xQueueBack = 0;

        // Sort all point by x-coordinate
        Arrays.sort(points, new X_Sort());
        TreeSet<PT> yWorkingSet = new TreeSet<>(new YX_Sort());

        PT pt1 = null, pt2 = null;
        double d = Double.POSITIVE_INFINITY;

        for (int i = 0; i < n; i++) {
        
            branchId = 2;
            instrumentation.coverBranch(branchId);

            PT nextPoint = points[i];

            // Remove all points (from both sets) where the distance to
            // the new point is greater than d (the smallest window size yet)
            while (xQueueFront != xQueueBack && nextPoint.x - points[xQueueFront].x > d) {

                branchId = 3;
                instrumentation.coverBranch(branchId);

                PT pt = points[xQueueFront++];
                yWorkingSet.remove(pt);
            }

            // Look at all the points in our working set with a y-coordinate
            // above nextPoint.y but within a window of nextPoint.y + d
            double upperBound = nextPoint.y + d;
            PT next = yWorkingSet.higher(nextPoint);
            while (next != null && next.y <= upperBound) {

                branchId = 4;
                instrumentation.coverBranch(branchId);

                double dist = nextPoint.dist(next);
                if (dist < d) {
                    branchId = 5;
                    instrumentation.coverBranch(branchId);

                    pt1 = nextPoint;
                    pt2 = next;
                    d = dist;
                } else {
                    branchId = 6;
                    instrumentation.coverBranch(branchId);
                }
                next = yWorkingSet.higher(next);
        }

        // Look at all the points in our working set with a y-coordinate
        // below nextPoint.y but within a window of nextPoint.y - d
        double lowerBound = nextPoint.y - d;
        next = yWorkingSet.lower(nextPoint);
        while (next != null && next.y > lowerBound) {

            branchId = 7;
            instrumentation.coverBranch(branchId);

            double dist = nextPoint.dist(next);
            if (dist < d) {

                branchId = 8;
                instrumentation.coverBranch(branchId);

                pt1 = nextPoint;
                pt2 = next;
                d = dist;
            } else {
                branchId = 9;
                instrumentation.coverBranch(branchId);
            }
            next = yWorkingSet.lower(next);
        }

        // Duplicate/stacked points
        if (yWorkingSet.contains(nextPoint)) {

            branchId = 10;
            instrumentation.coverBranch(branchId);
            
            pt1 = pt2 = nextPoint;
            d = 0;
            break;
        } else {
            branchId = 11;
            instrumentation.coverBranch(branchId);
        }

        // Add the next point to the working set
        yWorkingSet.add(nextPoint);
        xQueueBack++;
        }

        return new PT[] {pt1, pt2};
    }

    public static void main(String args[]){
        
        ClosestPairOfPoints p = new ClosestPairOfPoints();
        PT[] points = new PT[]{
            p.new PT(1,1),
            p.new PT(0,5),
            p.new PT(7,0),
            p.new PT(4,4)
        };

        PT[] closestPair = closestPair(points);

        System.out.println("Closest pair: " + closestPair[0].x + closestPair[0].y + "  " + closestPair[1].x + closestPair[1].y);

        String report = instrumentation.getReport();

        System.out.println(report);

    }
}
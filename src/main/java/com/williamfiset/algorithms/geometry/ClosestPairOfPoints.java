/**
 * This file contains code to find the closest pair of points given a list of points.
 *
 * <p>Time Complexity: O(nlog(n))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.lang.reflect.Type;
import java.util.*;

public class ClosestPairOfPoints {

  private static final double EPS = 1e-9;

  public class PT implements Type{
    double x, y;

    public PT(){}

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

  /**
   * Search above or below the current point for potential closer points 
   * */ 
  private static Object[] searchPoints(TreeSet<PT> yWorkingSet, PT nextPoint, double d, boolean searchAbove, PT pt1prev, PT pt2prev) {
    Object pt1 = pt1prev;
    Object pt2 = pt2prev;
    double bound = searchAbove ? nextPoint.y + d : nextPoint.y - d;
    PT next = searchAbove ? yWorkingSet.higher(nextPoint) : yWorkingSet.lower(nextPoint);
    while (next != null && (searchAbove ? next.y <= bound : next.y > bound)) {
      double dist = nextPoint.dist(next);
      if (dist < d) {
        pt1 = nextPoint;
        pt2 = next;
        d = dist;
      }
      next = searchAbove ? yWorkingSet.higher(next) : yWorkingSet.lower(next);
    }

    return new Object[]{pt1, pt2, Double.valueOf(d)};
  }


  // Finds the closest pair of points in a list of points
  public static PT[] closestPair(PT[] points) {

    if (points == null || points.length < 2) return new PT[] {};

    final int n = points.length;
    int xQueueFront = 0, xQueueBack = 0;

    // Sort all point by x-coordinate
    Arrays.sort(points, new X_Sort());
    TreeSet<PT> yWorkingSet = new TreeSet<>(new YX_Sort());

    PT pt1 = null, pt2 = null;
    double d = Double.POSITIVE_INFINITY;

    for (int i = 0; i < n; i++) {

      PT nextPoint = points[i];

      // Remove all points (from both sets) where the distance to
      // the new point is greater than d (the smallest window size yet)
      while (xQueueFront != xQueueBack && nextPoint.x - points[xQueueFront].x > d) {
        PT pt = points[xQueueFront++];
        yWorkingSet.remove(pt);
      }
      
      // Look at all the points in our working set with a y-coordinate
      // above nextPoint.y but within a window of nextPoint.y + d
      
      Object[] pointsAndDist = new Object[3];

      // Look at all the points in our working set with a y-coordinate
      // above nextPoint.y but within a window of nextPoint.y + d
      pointsAndDist = searchPoints(yWorkingSet, nextPoint, d, true, pt1, pt2);
      pt1 = (PT) pointsAndDist[0];
      pt2 = (PT)  pointsAndDist[1];
      d = (Double) pointsAndDist[2];
      // Look at all the points in our working set with a y-coordinate
      // below nextPoint.y but within a window of nextPoint.y - d
      pointsAndDist = searchPoints(yWorkingSet, nextPoint, d, false, pt1, pt2);
      pt1 = (PT) pointsAndDist[0];
      pt2 = (PT)  pointsAndDist[1];
      d = (Double) pointsAndDist[2];
      

      // Duplicate/stacked points
      if (yWorkingSet.contains(nextPoint)) {
        pt1 = pt2 = nextPoint;
        d = 0;
        break;
      }

      // Add the next point to the working set
      yWorkingSet.add(nextPoint);
      xQueueBack++;
    }

    return new PT[] {pt1, pt2};
  }
}

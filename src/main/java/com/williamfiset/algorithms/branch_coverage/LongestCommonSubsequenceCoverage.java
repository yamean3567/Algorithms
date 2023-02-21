package com.williamfiset.algorithms.branch_coverage;

public class LongestCommonSubsequence {

  public static AdHoc instrumentation = new AdHoc(15);


  // Returns a non unique Longest Common Subsequence
  // between the strings str1 and str2 in O(nm)
  public static String lcs(char[] A, char[] B) {

     int branchId = 0;

     if (A == null){
        branchId = 0;
        instrumentation.coverBranch(branchId);
        return null;
     }
     else if (B == null){
        branchId = 1;
        instrumentation.coverBranch(branchId);
        return null;
     }
     else {
        branchId = 2;
        instrumentation.coverBranch(branchId);
     }

    final int n = A.length;
    final int m = B.length;


     if (n == 0){
        branchId = 3;
        instrumentation.coverBranch(branchId);
        return null;
     }
     else if (m == 0){
        branchId = 4;
        instrumentation.coverBranch(branchId);
        return null;
     }
     else {
        branchId = 5;
        instrumentation.coverBranch(branchId);
     }

    int[][] dp = new int[n + 1][m + 1];

    // Suppose A = a1a2..an-1an and B = b1b2..bn-1bn
    for (int i = 1; i <= n; i++) {
        branchId = 6;
        instrumentation.coverBranch(branchId);
      for (int j = 1; j <= m; j++) {
        branchId = 7;
        instrumentation.coverBranch(branchId);

        // If ends match the LCS(a1a2..an-1an, b1b2..bn-1bn) = LCS(a1a2..an-1, b1b2..bn-1) + 1
        if (A[i - 1] == B[j - 1]){
            branchId = 8;
            instrumentation.coverBranch(branchId);
            dp[i][j] = dp[i - 1][j - 1] + 1;
        } 

        // If the ends do not match the LCS of a1a2..an-1an and b1b2..bn-1bn is
        // max( LCS(a1a2..an-1, b1b2..bn-1bn), LCS(a1a2..an-1an, b1b2..bn-1) )
        else {
            branchId = 9;
            instrumentation.coverBranch(branchId);
            dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    int lcsLen = dp[n][m];
    char[] lcs = new char[lcsLen];
    int index = 0;

    // Backtrack to find a LCS. We search for the cells
    // where we included an element which are those with
    // dp[i][j] != dp[i-1][j] and dp[i][j] != dp[i][j-1])
    int i = n, j = m;
    while (i >= 1 && j >= 1) {
      branchId = 10;
      instrumentation.coverBranch(branchId);

      int v = dp[i][j];

      // The order of these may output different LCSs
      while (i > 1 && dp[i - 1][j] == v){
         branchId = 11;
         instrumentation.coverBranch(branchId);
         i--;
      } 
      while (j > 1 && dp[i][j - 1] == v){
         branchId = 12;
         instrumentation.coverBranch(branchId);
         j--;
      } 

      // Make sure there is a match before adding
      if (v > 0){ 
         branchId = 13;
         instrumentation.coverBranch(branchId);
         lcs[lcsLen - index++ - 1] = A[i - 1]; // or B[j-1];
      }
      else {
         branchId = 14;
         instrumentation.coverBranch(branchId);
      }

      i--;
      j--;
    }

    return new String(lcs, 0, lcsLen);
  }

  public static void main(String[] args) {

    char[] A = {'A', 'X', 'B', 'C', 'Y'};
    char[] B = {'Z', 'A', 'Y', 'W', 'B', 'C'};
    System.out.println(lcs(A, B)); // ABC

    A = new char[] {'3', '9', '8', '3', '9', '7', '9', '7', '0'};
    B = new char[] {'3', '3', '9', '9', '9', '1', '7', '2', '0', '6'};
    System.out.println(lcs(A, B)); // 339970
  }
}

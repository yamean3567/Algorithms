package com.williamfiset.algorithms.branch_coverage;

import java.lang.StringBuilder;

/**
 * Class provides functions that can be used for manual instrumentation of
 * code, and reporting branch coverage.
 */
public class AdHoc {
    /** Holds whether branch is covered or not */
    private boolean[] coveredBranch;

    /** Total number of branches that can be covered */
    private int numberOfBranches;

    /** 
     * Constructs an object of this class with given number of branches
     * 
     * @param numberOfBranches the number of branches that can be covered
    */
    public AdHoc(int numberOfBranches) {
        this.coveredBranch = new boolean[numberOfBranches];
        this.numberOfBranches = numberOfBranches;
    }

    /**
     * Mark branch as covered
     * 
     * @param id the id of the branch that is marked as covered
     */
    public void coverBranch(int id) {
        coveredBranch[id] = true;
    } 

    /**
     * Get total branch coverage
     * 
     * @return the ratio of branches covered
     */
    public float branchCoverage() {
        int coveredBranches = 0;

        for (boolean b : coveredBranch) {
            if (b) coveredBranches++;
        }

        return (float) coveredBranches / this.numberOfBranches;
    }

    /**
     * Get branch report
     * 
     * @return report as a string, including whether each branch has been covered, and total
     * branch coverage in percent
     */
    public String getReport() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-15s %-15s\n", "Branch ID", "Taken"));

        for (int i = 0; i < this.numberOfBranches; ++i) {
            String taken = coveredBranch[i] ? "Yes" : "No";

            sb.append(String.format("%-15s %-15s\n", i, taken));
        }

        sb.append(String.format("\nBranch coverage: %.2f%%\n", branchCoverage() * 100));

        return sb.toString();
    }
}

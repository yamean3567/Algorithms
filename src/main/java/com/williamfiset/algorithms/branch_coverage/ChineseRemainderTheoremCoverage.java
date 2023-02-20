package com.williamfiset.algorithms.branch_coverage;

import java.util.*;

public class ChineseRemainderTheoremCoverage {

    // Just to test the branch coverage tool
    public static void main(String[] args) {
        long[] a = {1, 2, 3, 4, 5};
        long[] m = {2, 3, 4, 5, 6};
        reduce(a, m);
    }

    // Used for manual instrumentation
    public static AdHoc instrumentation = new AdHoc(16);


    /**
     * Use the chinese remainder theorem to solve a set of congruence equations.
     *
     * <p>The first method (eliminateCoefficient) is used to reduce an equation of the form cx≡a(mod
     * m)cx≡a(mod m) to the form x≡a_new(mod m_new)x≡anew(mod m_new), which gets rids of the
     * coefficient. A value of null is returned if the coefficient cannot be eliminated.
     *
     * <p>The second method (reduce) is used to reduce a set of equations so that the moduli become
     * pairwise co-prime (which means that we can apply the Chinese Remainder Theorem). The input and
     * output are of the form x≡a_0(mod m_0),...,x≡a_n−1(mod m_n−1)x≡a_0(mod m_0),...,x≡a_n−1(mod
     * m_n−1). Note that the number of equations may change during this process. A value of null is
     * returned if the set of equations cannot be reduced to co-prime moduli.
     *
     * <p>The third method (crt) is the actual Chinese Remainder Theorem. It assumes that all pairs of
     * moduli are co-prime to one another. This solves a set of equations of the form x≡a_0(mod
     * m_0),...,x≡v_n−1(mod m_n−1)x≡a_0(mod m_0),...,x≡v_n−1(mod m_n−1). It's output is of the form
     * x≡a_new(mod m_new)x≡a_new(mod m_new).
     *
     * @author Micah Stairs
     */
        // eliminateCoefficient() takes cx≡a(mod m) and gives x≡a_new(mod m_new).
        public static long[] eliminateCoefficient(long c, long a, long m) {

            long d = egcd(c, m)[0];

            if (a % d != 0) return null;

            c /= d;
            a /= d;
            m /= d;

            long inv = egcd(c, m)[1];
            m = Math.abs(m);
            a = (((a * inv) % m) + m) % m;

            return new long[] {a, m};
        }

        // reduce() takes a set of equations and reduces them to an equivalent
        // set with pairwise co-prime moduli (or null if not solvable).
        public static long[][] reduce(long[] a, long[] m) {

            List<Long> aNew = new ArrayList<Long>();
            List<Long> mNew = new ArrayList<Long>();
            int branchID = 0;

            // Split up each equation into prime factors
            for (int i = 0; i < a.length; i++) {
                branchID = 0;
                ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                List<Long> factors = primeFactorization(m[i]);
                Collections.sort(factors);
                ListIterator<Long> iterator = factors.listIterator();
                while (iterator.hasNext()) {
                    branchID = 1;
                    ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                    long val = iterator.next();
                    long total = val;
                    while (iterator.hasNext()) {
                        branchID = 2;
                        ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                        long nextVal = iterator.next();
                        if (nextVal == val) {
                            branchID = 3;
                            ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                            total *= val;
                        } else {
                            branchID = 4;
                            ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                            iterator.previous();
                            break;
                        }
                    }
                    aNew.add(a[i] % total);
                    mNew.add(total);
                }
            }

            // Throw away repeated information and look for conflicts
            for (int i = 0; i < aNew.size(); i++) {
                branchID = 5;
                ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                for (int j = i + 1; j < aNew.size(); j++) {
                    branchID = 6;
                    ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                    if (mNew.get(i) % mNew.get(j) == 0 || mNew.get(j) % mNew.get(i) == 0) {
                        branchID = 7;
                        ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                        if (mNew.get(i) > mNew.get(j)) {
                            branchID = 8;
                            ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                            if ((aNew.get(i) % mNew.get(j)) == aNew.get(j)) {
                                branchID = 9;
                                ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                                aNew.remove(j);
                                mNew.remove(j);
                                j--;
                                continue;
                            } else {
                                branchID = 10;
                                ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                                return null;
                            }
                        } else {
                            branchID = 11;
                            ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                            if ((aNew.get(j) % mNew.get(i)) == aNew.get(i)) {
                                branchID = 12;
                                ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                                aNew.remove(i);
                                mNew.remove(i);
                                i--;
                                break;
                            } else {
                                branchID = 13;
                                ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                                return null;
                            }
                        }
                    }else{
                        branchID = 14;
                        ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                    }
                }
            }

            // Put result into an array
            long[][] res = new long[2][aNew.size()];
            for (int i = 0; i < aNew.size(); i++) {
                branchID = 15;
                ChineseRemainderTheoremCoverage.instrumentation.coverBranch(branchID);
                res[0][i] = aNew.get(i);
                res[1][i] = mNew.get(i);
            }
            String report = ChineseRemainderTheoremCoverage.instrumentation.getReport();
            System.out.println(report);
            return res;
        }

        public static long[] crt(long[] a, long[] m) {

            long M = 1;
            for (int i = 0; i < m.length; i++) M *= m[i];

            long[] inv = new long[a.length];
            for (int i = 0; i < inv.length; i++) inv[i] = egcd(M / m[i], m[i])[1];

            long x = 0;
            for (int i = 0; i < m.length; i++) {
                x += (M / m[i]) * a[i] * inv[i]; // Overflow could occur here
                x = ((x % M) + M) % M;
            }

            return new long[] {x, M};
        }

        private static ArrayList<Long> primeFactorization(long n) {
            ArrayList<Long> factors = new ArrayList<Long>();
            if (n <= 0) throw new IllegalArgumentException();
            else if (n == 1) return factors;
            PriorityQueue<Long> divisorQueue = new PriorityQueue<Long>();
            divisorQueue.add(n);
            while (!divisorQueue.isEmpty()) {
                long divisor = divisorQueue.remove();
                if (isPrime(divisor)) {
                    factors.add(divisor);
                    continue;
                }
                long next_divisor = pollardRho(divisor);
                if (next_divisor == divisor) {
                    divisorQueue.add(divisor);
                } else {
                    divisorQueue.add(next_divisor);
                    divisorQueue.add(divisor / next_divisor);
                }
            }
            return factors;
        }

        private static long pollardRho(long n) {
            if (n % 2 == 0) return 2;
            // Get a number in the range [2, 10^6]
            long x = 2 + (long) (999999 * Math.random());
            long c = 2 + (long) (999999 * Math.random());
            long y = x;
            long d = 1;
            while (d == 1) {
                x = (x * x + c) % n;
                y = (y * y + c) % n;
                y = (y * y + c) % n;
                d = gcf(Math.abs(x - y), n);
                if (d == n) break;
            }
            return d;
        }

        // Extended euclidean algorithm
        private static long[] egcd(long a, long b) {
            if (b == 0) return new long[] {a, 1, 0};
            else {
                long[] ret = egcd(b, a % b);
                long tmp = ret[1] - ret[2] * (a / b);
                ret[1] = ret[2];
                ret[2] = tmp;
                return ret;
            }
        }

        private static long gcf(long a, long b) {
            return b == 0 ? a : gcf(b, a % b);
        }

        private static boolean isPrime(long n) {
            if (n < 2) return false;
            if (n == 2 || n == 3) return true;
            if (n % 2 == 0 || n % 3 == 0) return false;

            int limit = (int) Math.sqrt(n);

            for (int i = 5; i <= limit; i += 6) if (n % i == 0 || n % (i + 2) == 0) return false;

            return true;
        }
}


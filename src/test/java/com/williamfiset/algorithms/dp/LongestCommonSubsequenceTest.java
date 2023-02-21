package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.*;

public class LongestCommonSubsequenceTest {

    @Test    
    public void testNullInput(){
        String res1 = LongestCommonSubsequence.lcs("1".toCharArray(), null);
        String res2 = LongestCommonSubsequence.lcs(null, "2".toCharArray());
        assertThat(res1 == null);
        assertThat(res2 == null);
    }

    @Test    
    public void testZeroLengthInput(){
        String res1 = LongestCommonSubsequence.lcs("1".toCharArray(), "".toCharArray());
        String res2 = LongestCommonSubsequence.lcs("".toCharArray(), "2".toCharArray());
        assertThat(res1 == null);
        assertThat(res2 == null);
    }

    @Test    
    public void testNoSubsequence(){
        String res1 = LongestCommonSubsequence.lcs("12345".toCharArray(), "6789qwe".toCharArray());
        String res2 = LongestCommonSubsequence.lcs("asdfgh".toCharArray(), "zxcvbn".toCharArray());
        assertThat(res1 == "");
        assertThat(res2 == "");
    }

    @Test    
    public void testWorkingInput(){
        char[] A = {'A', 'X', 'B', 'C', 'Y'};
        char[] B = {'Z', 'A', 'Y', 'W', 'B', 'C'};

        char[] C = {'3', '9', '8', '3', '9', '7', '9', '7', '0'};
        char[] D = {'3', '3', '9', '9', '9', '1', '7', '2', '0', '6'};

        String res1 = LongestCommonSubsequence.lcs(A, B);
        String res2 = LongestCommonSubsequence.lcs(C, D);

        assertThat(res1 == "ABC");
        assertThat(res2 == "339970");
    }

    @Test    
    public void testWorkingInput2(){
        char[] A = {'1', '3', '5', '7', '9'};
        char[] B = {'5', '3', '3', '7', '9', '9'};

        char[] C = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        char[] D = {'A', 'C', 'B', 'D', 'E', 'G', 'F', 'I', 'H', 'I'};

        String res1 = LongestCommonSubsequence.lcs(A, B);
        String res2 = LongestCommonSubsequence.lcs(C, D);

        assertThat(res1 == "379");
        assertThat(res2 == "ACDEGHI");
    }

}
    
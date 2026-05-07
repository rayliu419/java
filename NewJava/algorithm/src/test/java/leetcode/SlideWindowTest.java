package leetcode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SlideWindowTest {

    private final SlideWindow slideWindow = new SlideWindow();

    @Test
    public void testBasic() {
        assertEquals(3, slideWindow.lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    public void testAllSame() {
        assertEquals(1, slideWindow.lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    public void testNoRepeat() {
        assertEquals(6, slideWindow.lengthOfLongestSubstring("abcdef"));
    }

    @Test
    public void testEmpty() {
        assertEquals(0, slideWindow.lengthOfLongestSubstring(""));
    }

    @Test
    public void testSingleChar() {
        assertEquals(1, slideWindow.lengthOfLongestSubstring("a"));
    }

    @Test
    public void testTwoSame() {
        assertEquals(1, slideWindow.lengthOfLongestSubstring("aa"));
    }

    @Test
    public void testTwoDifferent() {
        assertEquals(2, slideWindow.lengthOfLongestSubstring("ab"));
    }

    @Test
    public void testRepeatAtEnd() {
        assertEquals(3, slideWindow.lengthOfLongestSubstring("abca"));
    }

    @Test
    public void testLongestInMiddle() {
        assertEquals(3, slideWindow.lengthOfLongestSubstring("aabcb"));
    }

    @Test
    public void testContainsSpaces() {
        assertEquals(3, slideWindow.lengthOfLongestSubstring("ab ba"));
    }

    @Test
    public void testAllUniqueLong() {
        assertEquals(10, slideWindow.lengthOfLongestSubstring("abcdefghij"));
    }

    @Test
    public void testRepeatsAcrossWindow() {
        assertEquals(2, slideWindow.lengthOfLongestSubstring("abababab"));
    }

    @Test
    public void testNumbers() {
        assertEquals(3, slideWindow.lengthOfLongestSubstring("12321"));
    }

    @Test
    public void testSpecialChars() {
        assertEquals(4, slideWindow.lengthOfLongestSubstring("a!b@b"));
    }

    @Test
    public void testMinSubArrayLenBasic() {
        assertEquals(2, slideWindow.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
    }

    @Test
    public void testMinSubArrayLenSingleMatch() {
        assertEquals(1, slideWindow.minSubArrayLen(4, new int[]{1, 4, 4}));
    }

    @Test
    public void testMinSubArrayLenNoSolution() {
        assertEquals(0, slideWindow.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
    }

    @Test
    public void testMinSubArrayLenExactOne() {
        assertEquals(1, slideWindow.minSubArrayLen(3, new int[]{3}));
    }

    @Test
    public void testMinSubArrayLenTooSmall() {
        assertEquals(0, slideWindow.minSubArrayLen(5, new int[]{3}));
    }

    @Test
    public void testMinSubArrayLenExactAll() {
        assertEquals(5, slideWindow.minSubArrayLen(15, new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testMinSubArrayLenFromComments() {
        assertEquals(1, slideWindow.minSubArrayLen(3, new int[]{4, 1, 2, 3}));
        assertEquals(1, slideWindow.minSubArrayLen(3, new int[]{1, 2, 3}));
    }

    @Test
    public void testMinSubArrayLenLargeTarget() {
        assertEquals(0, slideWindow.minSubArrayLen(100, new int[]{1, 2, 3}));
    }

    @Test
    public void testMinSubArrayLenAllEqual() {
        assertEquals(3, slideWindow.minSubArrayLen(6, new int[]{2, 2, 2, 2, 2}));
    }

    @Test
    public void testMinSubArrayLenTargetIsSumOfFirstFew() {
        assertEquals(2, slideWindow.minSubArrayLen(6, new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testMinWindowBasic() {
        assertEquals("BANC", slideWindow.minWindow("ADOBECODEBANC", "ABC"));
    }

    @Test
    public void testMinWindowSingleChar() {
        assertEquals("a", slideWindow.minWindow("a", "a"));
    }

    @Test
    public void testMinWindowNoSolution() {
        assertEquals("", slideWindow.minWindow("a", "b"));
    }

    @Test
    public void testMinWindowTwoSame() {
        assertEquals("aa", slideWindow.minWindow("aa", "aa"));
    }

    @Test
    public void testMinWindowNotEnough() {
        assertEquals("", slideWindow.minWindow("a", "aa"));
    }

    @Test
    public void testMinWindowTwoDistinct() {
        assertEquals("ab", slideWindow.minWindow("aaabbb", "ab"));
    }

    @Test
    public void testMinWindowDuplicateTarget() {
        assertEquals("abca", slideWindow.minWindow("abca", "aa"));
    }

    @Test
    public void testMinWindowExactMatch() {
        assertEquals("abc", slideWindow.minWindow("abc", "abc"));
    }

    @Test
    public void testMinWindowWindowInMiddle() {
        assertEquals("ba", slideWindow.minWindow("bba", "ab"));
    }

    @Test
    public void testMinWindowRepeatingS() {
        assertEquals("aa", slideWindow.minWindow("aaaaaaaa", "aa"));
    }

    @Test
    public void testMinWindowSingleInTarget() {
        assertEquals("a", slideWindow.minWindow("ab", "a"));
        assertEquals("b", slideWindow.minWindow("ab", "b"));
    }

    @Test
    public void testMinWindowLongerT() {
        assertEquals("", slideWindow.minWindow("a", "aa"));
    }

    @Test
    public void testMinWindowNotContiguous() {
        assertEquals("ab", slideWindow.minWindow("caab", "ab"));
    }

    @Test
    public void testMinWindowDuplicateInMiddle() {
        assertEquals("abbbbba", slideWindow.minWindow("abbbbba", "aa"));
    }

    @Test
    public void testMinWindowEmptyS() {
        assertEquals("", slideWindow.minWindow("", "a"));
    }

    @Test
    public void testMinWindowBothEmpty() {
        assertEquals("", slideWindow.minWindow("", ""));
    }

    @Test
    public void testMinWindowAdvanced() {
        assertEquals("aab", slideWindow.minWindow("aabdec", "aab"));
    }

    @Test
    public void testMinWindowAllSame() {
        assertEquals("aa", slideWindow.minWindow("aa", "aa"));
    }

    @Test
    public void testMinWindowTargetLongerThanS() {
        assertEquals("", slideWindow.minWindow("abc", "abcd"));
    }
}

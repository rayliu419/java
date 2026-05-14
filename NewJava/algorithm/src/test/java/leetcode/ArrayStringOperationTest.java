package leetcode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayStringOperationTest {

    private final ArrayStringOperation arrayStringOperation = new ArrayStringOperation();

    @Test
    public void testRemoveSpaceNormal() {
        assertEquals("hello world", arrayStringOperation.removeSpace("hello world"));
    }

    @Test
    public void testRemoveSpaceLeadingSpaces() {
        assertEquals("abc", arrayStringOperation.removeSpace("  abc"));
    }

    @Test
    public void testRemoveSpaceTrailingSpaces() {
        assertEquals("abc", arrayStringOperation.removeSpace("abc   "));
    }

    @Test
    public void testRemoveSpaceMultipleSpacesBetweenWords() {
        assertEquals("a b c", arrayStringOperation.removeSpace("a   b   c"));
    }

    @Test
    public void testRemoveSpaceMixed() {
        assertEquals("hello world", arrayStringOperation.removeSpace("  hello   world  "));
    }

    @Test
    public void testRemoveSpaceSingleChar() {
        assertEquals("a", arrayStringOperation.removeSpace(" a "));
    }

    @Test
    public void testRemoveSpaceEmptyString() {
        assertEquals("", arrayStringOperation.removeSpace(""));
    }

    @Test
    public void testRemoveSpaceAllSpaces() {
        assertEquals("", arrayStringOperation.removeSpace("   "));
    }

    @Test
    public void testRemoveSpaceSingleWordWithLeadingSpaces() {
        assertEquals("word", arrayStringOperation.removeSpace("     word"));
    }

    @Test
    public void testRemoveSpaceSingleWordWithTrailingSpaces() {
        assertEquals("word", arrayStringOperation.removeSpace("word     "));
    }

    @Test
    public void testRemoveSpaceManyWords() {
        assertEquals("a b c d e", arrayStringOperation.removeSpace("  a   b   c   d   e  "));
    }

    @Test
    public void testRemoveSpaceAlreadyTrimmed() {
        assertEquals("hello world", arrayStringOperation.removeSpace("hello world"));
    }

    @Test
    public void testRemoveSpaceOnlyOneCharNoSpace() {
        assertEquals("x", arrayStringOperation.removeSpace("x"));
    }

    @Test
    public void testAddStringsNormal() {
        assertEquals("579", arrayStringOperation.addStrings("123", "456"));
    }

    @Test
    public void testAddStringsWithCarry() {
        assertEquals("1000", arrayStringOperation.addStrings("999", "1"));
    }

    @Test
    public void testAddStringsDifferentLength() {
        assertEquals("168", arrayStringOperation.addStrings("123", "45"));
    }

    @Test
    public void testAddStringsBothZero() {
        assertEquals("0", arrayStringOperation.addStrings("0", "0"));
    }

    @Test
    public void testAddStringsOneZero() {
        assertEquals("123", arrayStringOperation.addStrings("0", "123"));
    }

    @Test
    public void testAddStringsSingleDigitCarry() {
        assertEquals("18", arrayStringOperation.addStrings("9", "9"));
    }

    @Test
    public void testAddStringsLarge() {
        assertEquals("1000000000000000000000000000000",
                arrayStringOperation.addStrings("999999999999999999999999999999", "1"));
    }
}

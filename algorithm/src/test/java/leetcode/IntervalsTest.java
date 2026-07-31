package leetcode;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntervalsTest {

    @Test
    public void testMergeBasic() {
        int[][] input = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] expected = {{1, 6}, {8, 10}, {15, 18}};
        assertTrue(Arrays.deepEquals(expected, Intervals.merge(input)));
    }

    @Test
    public void testMergeContained() {
        int[][] input = {{1, 4}, {0, 4}};
        int[][] expected = {{0, 4}};
        assertTrue(Arrays.deepEquals(expected, Intervals.merge(input)));
    }

    @Test
    public void testMergeNoOverlap() {
        int[][] input = {{1, 2}, {3, 4}, {5, 6}};
        int[][] expected = {{1, 2}, {3, 4}, {5, 6}};
        assertTrue(Arrays.deepEquals(expected, Intervals.merge(input)));
    }

    @Test
    public void testMergeSingleInterval() {
        int[][] input = {{1, 5}};
        int[][] expected = {{1, 5}};
        assertTrue(Arrays.deepEquals(expected, Intervals.merge(input)));
    }

    @Test
    public void testMergeAllOverlapping() {
        int[][] input = {{1, 10}, {2, 3}, {4, 5}};
        int[][] expected = {{1, 10}};
        assertTrue(Arrays.deepEquals(expected, Intervals.merge(input)));
    }

    @Test
    public void testEraseOverlapBasic() {
        int[][] input = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        assertEquals(1, Intervals.eraseOverlapIntervals(input));
    }

    @Test
    public void testEraseOverlapNoOverlap() {
        int[][] input = {{1, 2}, {3, 4}};
        assertEquals(0, Intervals.eraseOverlapIntervals(input));
    }

    @Test
    public void testEraseOverlapAllOverlap() {
        int[][] input = {{1, 3}, {1, 3}, {1, 3}};
        assertEquals(2, Intervals.eraseOverlapIntervals(input));
    }

    @Test
    public void testEraseOverlapSingleInterval() {
        int[][] input = {{1, 5}};
        assertEquals(0, Intervals.eraseOverlapIntervals(input));
    }

    @Test
    public void testInsertMiddle() {
        int[][] input = {{1, 3}, {6, 9}};
        int[] newInterval = {2, 5};
        int[][] expected = {{1, 5}, {6, 9}};
        assertTrue(Arrays.deepEquals(expected, Intervals.insert(input, newInterval)));
    }

    @Test
    public void testInsertMergeMultiple() {
        int[][] input = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval = {4, 8};
        int[][] expected = {{1, 2}, {3, 10}, {12, 16}};
        assertTrue(Arrays.deepEquals(expected, Intervals.insert(input, newInterval)));
    }

    @Test
    public void testInsertNoOverlapAtEnd() {
        int[][] input = {{1, 3}, {6, 9}};
        int[] newInterval = {10, 12};
        int[][] expected = {{1, 3}, {6, 9}, {10, 12}};
        assertTrue(Arrays.deepEquals(expected, Intervals.insert(input, newInterval)));
    }

    @Test
    public void testInsertNoOverlapAtStart() {
        int[][] input = {{6, 9}, {10, 12}};
        int[] newInterval = {1, 3};
        int[][] expected = {{1, 3}, {6, 9}, {10, 12}};
        assertTrue(Arrays.deepEquals(expected, Intervals.insert(input, newInterval)));
    }

    @Test
    public void testInsertCoverAll() {
        int[][] input = {{2, 3}, {4, 5}};
        int[] newInterval = {1, 6};
        int[][] expected = {{1, 6}};
        assertTrue(Arrays.deepEquals(expected, Intervals.insert(input, newInterval)));
    }

    @Test
    public void testInsertEmptyIntervals() {
        int[][] input = {};
        int[] newInterval = {2, 5};
        int[][] expected = {{2, 5}};
        assertTrue(Arrays.deepEquals(expected, Intervals.insert(input, newInterval)));
    }

    @Test
    public void testMinMeetingRoomsBasic() {
        int[][] input = {{0, 30}, {5, 10}, {15, 20}};
        assertEquals(2, Intervals.minMeetingRooms(input));
    }

    @Test
    public void testMinMeetingRoomsNoOverlap() {
        int[][] input = {{0, 5}, {5, 10}, {10, 15}};
        assertEquals(1, Intervals.minMeetingRooms(input));
    }

    @Test
    public void testMinMeetingRoomsAllOverlap() {
        int[][] input = {{0, 10}, {1, 9}, {2, 8}};
        assertEquals(3, Intervals.minMeetingRooms(input));
    }

    @Test
    public void testMinMeetingRoomsSingle() {
        int[][] input = {{1, 5}};
        assertEquals(1, Intervals.minMeetingRooms(input));
    }

    @Test
    public void testMinMeetingRoomsOneAtATime() {
        int[][] input = {{0, 2}, {2, 4}, {4, 6}};
        assertEquals(1, Intervals.minMeetingRooms(input));
    }

    @Test
    public void testMinMeetingRoomsEmpty() {
        int[][] input = {};
        assertEquals(0, Intervals.minMeetingRooms(input));
    }
}

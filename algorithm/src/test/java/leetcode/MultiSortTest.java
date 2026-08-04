package leetcode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MultiSortTest {

    private MultiSort.Item item(int value, String time) {
        MultiSort.Item item = new MultiSort.Item();
        item.setValue(value);
        item.setTime(time);
        return item;
    }

    @Test
    public void testGetTimeMidnight() {
        assertEquals(0, MultiSort.getTime("00:00:00"));
    }

    @Test
    public void testGetTimeSeconds() {
        assertEquals(59, MultiSort.getTime("00:00:59"));
    }

    @Test
    public void testGetTimeMinutes() {
        assertEquals(60, MultiSort.getTime("00:01:00"));
    }

    @Test
    public void testGetTimeNormal() {
        assertEquals(45045, MultiSort.getTime("12:30:45"));
    }

    @Test
    public void testGetTimeEndOfDay() {
        assertEquals(86399, MultiSort.getTime("23:59:59"));
    }

    @Test
    public void testGetTimeLeadingZeros() {
        assertEquals(3723, MultiSort.getTime("01:02:03"));
    }

    @Test
    public void testSortByValueOnly() {
        MultiSort.Item[] items = {
                item(5, "10:00:00"),
                item(3, "09:00:00"),
                item(8, "08:00:00")
        };
        MultiSort.multiSort(items);
        assertEquals(3, items[0].getValue());
        assertEquals(5, items[1].getValue());
        assertEquals(8, items[2].getValue());
    }

    @Test
    public void testSortByTimeWhenValueEqual() {
        MultiSort.Item[] items = {
                item(5, "10:00:00"),
                item(3, "09:00:00"),
                item(5, "08:00:00")
        };
        MultiSort.multiSort(items);
        assertEquals(3, items[0].getValue());
        assertEquals(5, items[1].getValue());
        assertEquals("08:00:00", items[1].getTime());
        assertEquals("10:00:00", items[2].getTime());
    }

    @Test
    public void testSortMixedValuesAndTimes() {
        MultiSort.Item[] items = {
                item(2, "23:59:59"),
                item(1, "00:00:01"),
                item(2, "00:00:00"),
                item(1, "23:59:59")
        };
        MultiSort.multiSort(items);
        assertEquals(1, items[0].getValue());
        assertEquals("00:00:01", items[0].getTime());
        assertEquals(1, items[1].getValue());
        assertEquals("23:59:59", items[1].getTime());
        assertEquals(2, items[2].getValue());
        assertEquals("00:00:00", items[2].getTime());
        assertEquals(2, items[3].getValue());
        assertEquals("23:59:59", items[3].getTime());
    }

    @Test
    public void testSortStableWhenValueAndTimeEqual() {
        MultiSort.Item first = item(7, "12:00:00");
        MultiSort.Item second = item(7, "12:00:00");
        MultiSort.Item[] items = {second, first};
		assertSame(second, items[0]); // 稳定排序保持原相对顺序：输入中 second 在前
		assertSame(first, items[1]);
    }

    @Test
    public void testSortEmptyArray() {
        MultiSort.multiSort(new MultiSort.Item[0]);
    }

    @Test
    public void testSortSingleElement() {
        MultiSort.Item[] items = {item(1, "12:00:00")};
        MultiSort.multiSort(items);
        assertEquals(1, items[0].getValue());
    }
}

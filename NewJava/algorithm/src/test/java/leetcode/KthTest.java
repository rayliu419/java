package leetcode;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class KthTest {

    @Test
    public void testQuickSortWithThreeElements() {
        Kth kth = new Kth();
        int[] array = new int[]{1, 2, 3};
        kth.quickSort(array);
        assertTrue(Arrays.equals(new int[]{1, 2, 3}, array));
    }

    @Test
    public void testQuickSortWithTwoElements() {
        Kth kth = new Kth();
        int[] array = new int[]{3, 2};
        kth.quickSort(array);
        assertTrue(Arrays.equals(new int[]{2, 3}, array));
    }

    @Test
    public void testQuickSortWithDuplicateElements() {
        Kth kth = new Kth();
        int[] array = new int[]{3, 1, 2, 3};
        kth.quickSort(array);
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 3}, array));
    }

    @Test
    public void testQuickSortWithAlreadySorted() {
        Kth kth = new Kth();
        int[] array = new int[]{1, 2, 3, 4, 5};
        kth.quickSort(array);
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 5}, array));
    }

    @Test
    public void testQuickSortWithReverseSorted() {
        Kth kth = new Kth();
        int[] array = new int[]{5, 4, 3, 2, 1};
        kth.quickSort(array);
        assertTrue(Arrays.equals(new int[]{1, 2, 3, 4, 5}, array));
    }

    @Test
    public void testQuickSortWithSingleElement() {
        Kth kth = new Kth();
        int[] array = new int[]{1};
        kth.quickSort(array);
        assertTrue(Arrays.equals(new int[]{1}, array));
    }

    @Test
    public void testQuickSortWithEmptyArray() {
        Kth kth = new Kth();
        int[] array = new int[]{};
        kth.quickSort(array);
        assertTrue(Arrays.equals(new int[]{}, array));
    }

// ===================================================================================================================

    @Test
    public void testFindKthElementWithNormalCase() {
        Kth kth = new Kth();
        int[] array = new int[]{3, 1, 2};
        assertTrue(kth.findKthElement(array, 2) == 2);
    }


    @Test
    public void testFindKthElementWithFirstElement() {
        Kth kth = new Kth();
        int[] array = new int[]{3, 1, 2};
        assertTrue(kth.findKthElement(array, 1) == 1);
    }


    @Test
    public void testFindKthElementWithLastElement() {
        Kth kth = new Kth();
        int[] array = new int[]{1, 2, 3};
        assertTrue(kth.findKthElement(array, 3) == 3);
    }


    @Test
    public void testFindKthElementWithDuplicateElements() {
        Kth kth = new Kth();
        int[] array = new int[]{3, 1, 2, 3};
        assertTrue(kth.findKthElement(array, 2) == 2);
    }


    @Test
    public void testFindKthElementWithAlreadySorted() {
        Kth kth = new Kth();
        int[] array = new int[]{1, 2, 3, 4, 5};
        assertTrue(kth.findKthElement(array, 3) == 3);
    }


    @Test
    public void testFindKthElementWithReverseSorted() {
        Kth kth = new Kth();
        int[] array = new int[]{5, 4, 3, 2, 1};
        assertTrue(kth.findKthElement(array, 2) == 2);
    }


    @Test
    public void testFindKthElementWithSingleElement() {
        Kth kth = new Kth();
        int[] array = new int[]{1};
        assertTrue(kth.findKthElement(array, 1) == 1);
    }


    @Test
    public void testFindKthElementWithKExceedsLength() {
        Kth kth = new Kth();
        int[] array = new int[]{1, 2, 3};
        assertTrue(kth.findKthElement(array, 5) == -1);
    }


    @Test
    public void testFindKthElementWithEmptyArray() {
        Kth kth = new Kth();
        int[] array = new int[]{};
        assertTrue(kth.findKthElement(array, 1) == -1);
    }

}

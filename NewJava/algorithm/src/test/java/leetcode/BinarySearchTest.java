package leetcode;

import junit.framework.TestCase;
import org.junit.Test;

public class BinarySearchTest extends TestCase {

    @Test
    public void test() {
        BinarySearch binarySearch = new BinarySearch();
        int index = binarySearch.searchInsert(new int[]{1, 3, 5, 6}, 5);
        System.out.println(index);

        int index2 = binarySearch.searchInsert(new int[]{1, 3, 5, 6}, 2);
        System.out.println(index2);

        int index3 = binarySearch.searchInsert(new int[]{1, 3, 5, 6}, 7);
        System.out.println(index3);
    }

    @Test
    public void test2() {
        BinarySearch binarySearch = new BinarySearch();

//        int number = binarySearch.singleNonDuplicate(new int[]{1, 2, 2});
//        System.out.println(number);
//
//        int number2 = binarySearch.singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8});
//        System.out.println(number2);
//
//        int number3 = binarySearch.singleNonDuplicate(new int[]{3, 3, 7, 7, 10, 11, 11});
//        System.out.println(number3);
//
//        int number4 = binarySearch.singleNonDuplicate(new int[]{1, 1, 2});
//        System.out.println(number4);
//
//        int number5 = binarySearch.singleNonDuplicate(new int[]{1, 1, 2, 3, 3});
//        System.out.println(number5);
    }

}
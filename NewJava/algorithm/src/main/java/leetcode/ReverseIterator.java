package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ReverseIterator {

    public void reverseOutput() {
        ArrayList arrayList = new ArrayList(List.of(1, 2, 3, 4));

        //java ArrayList 不太好用，容易错，一般不用
        ListIterator reverseIterator = arrayList.listIterator(arrayList.size());
        while (reverseIterator.hasPrevious()) {
            System.out.println(reverseIterator.previous());
        }
    }
}

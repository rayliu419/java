package main.datastructure;

import java.util.ArrayDeque;
import java.util.Stack;

public class JavaStack {

    public static void StackBasic() {
        Stack<MyItem> stack = new Stack<>();
        
        stack.push(new MyItem("a", 1, 1));
        stack.push(new MyItem("b", 2, 2));
        stack.push(new MyItem("c", 3, 3));

        while (!stack.isEmpty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }

    public static void StackWithArrayDeque() {
        ArrayDeque<MyItem> arrayDeque = new ArrayDeque();
        arrayDeque.push(new MyItem("a", 1, 1));
        arrayDeque.push(new MyItem("b", 2, 2));
        arrayDeque.push(new MyItem("c", 3, 3));

        while (!arrayDeque.isEmpty()) {
            System.out.println(arrayDeque.peek());
            arrayDeque.pop();
        }
    }
}

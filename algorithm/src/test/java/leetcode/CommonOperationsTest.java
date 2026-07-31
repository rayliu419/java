package leetcode;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CommonOperationsTest {

    private final CommonOperations ops = new CommonOperations();

    @Test
    public void countString_groupsIndicesByWord() {
        String[] words = {"a", "b", "a", "c", "b", "a"};
        Map<String, List<Integer>> result = ops.countString(words);

        assertEquals(List.of(0, 2, 5), result.get("a"));
        assertEquals(List.of(1, 4), result.get("b"));
        assertEquals(List.of(3), result.get("c"));
        assertEquals(3, result.size());
    }

    @Test
    public void countString_emptyInput() {
        Map<String, List<Integer>> result = ops.countString(new String[]{});
        assertTrue(result.isEmpty());
    }

    @Test
    public void countString_noDuplicates() {
        String[] words = {"x", "y", "z"};
        Map<String, List<Integer>> result = ops.countString(words);

        assertEquals(List.of(0), result.get("x"));
        assertEquals(List.of(1), result.get("y"));
        assertEquals(List.of(2), result.get("z"));
    }

    @Test
    public void wordCount_countsFrequency() {
        String[] tokens = {"apple", "banana", "apple", "orange", "banana", "apple"};
        Map<String, Integer> result = ops.wordCount(tokens);

      assertEquals(3, result.get("apple").intValue());
        assertEquals(2, result.get("banana").intValue());
        assertEquals(1, result.get("orange").intValue());
    }

    @Test
    public void wordCount_emptyInput() {
        assertTrue(ops.wordCount(new String[]{}).isEmpty());
    }

    @Test
    public void wordCount_singleElement() {
        assertEquals(1, ops.wordCount(new String[]{"only"}).get("only").intValue());
    }

    @Test
    public void buildGraph_bidirectionalEdges() {
        List<String[]> edges = List.of(
                new String[]{"A", "B"},
                new String[]{"A", "C"},
                new String[]{"B", "D"}
        );
        Map<String, List<String>> graph = ops.buildGraph(edges);

        assertEquals(List.of("B", "C"), graph.get("A"));
        assertTrue(graph.get("B").contains("A"));
        assertTrue(graph.get("B").contains("D"));
        assertEquals(List.of("A"), graph.get("C"));
        assertEquals(List.of("B"), graph.get("D"));
    }

    @Test
    public void buildGraph_emptyInput() {
        assertTrue(ops.buildGraph(List.of()).isEmpty());
    }

    @Test
    public void mergeCart_aggregatesSameSku() {
        List<CommonOperations.CartItem> items = List.of(
                new CommonOperations.CartItem("sku1", 2, 10),
                new CommonOperations.CartItem("sku1", 1, 10),
                new CommonOperations.CartItem("sku2", 3, 20)
        );
        Map<String, Integer> cart = ops.mergeCart(items);

        assertEquals(3, cart.get("sku1").intValue());
        assertEquals(3, cart.get("sku2").intValue());
    }

    @Test
    public void mergeCart_emptyInput() {
        assertTrue(ops.mergeCart(List.of()).isEmpty());
    }
}

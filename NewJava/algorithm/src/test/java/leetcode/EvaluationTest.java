package leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class EvaluationTest {

    private final Evaluation eval = new Evaluation();

    // ========== calculateAddMinus ==========

    @Test
    public void addMinus_simpleAddition() {
        assertEquals(3, eval.calculateAddMinus("1+2"));
    }

    @Test
    public void addMinus_simpleSubtraction() {
        assertEquals(2, eval.calculateAddMinus("5-3"));
    }

    @Test
    public void addMinus_mixed() {
        assertEquals(0, eval.calculateAddMinus("1+2-3"));
    }

    @Test
    public void addMinus_multiDigit() {
        assertEquals(46, eval.calculateAddMinus("12+34"));
    }

    @Test
    public void addMinus_leadingMinus() {
        assertEquals(2, eval.calculateAddMinus("-3+5"));
    }

    @Test
    public void addMinus_singleNumber() {
        assertEquals(42, eval.calculateAddMinus("42"));
    }

    @Test
    public void addMinus_empty() {
        assertEquals(0, eval.calculateAddMinus(""));
    }

    @Test
    public void addMinus_negativeResult() {
        assertEquals(-5, eval.calculateAddMinus("3-8"));
    }

    @Test
    public void addMinus_longExpression() {
        assertEquals(28, eval.calculateAddMinus("10+20-5+3"));
    }

    // ========== calculateMultiplyDivide ==========

    @Test
    public void mulDiv_addition() {
        assertEquals(5, Evaluation.calculateMultiplyDivide("2+3"));
    }

    @Test
    public void mulDiv_subtraction() {
        assertEquals(1, Evaluation.calculateMultiplyDivide("3-2"));
    }

    @Test
    public void mulDiv_multiplicationBeforeAddition() {
        assertEquals(7, Evaluation.calculateMultiplyDivide("3+2*2"));
    }

    @Test
    public void mulDiv_divisionBeforeSubtraction() {
        assertEquals(1, Evaluation.calculateMultiplyDivide(" 3/2 "));
    }

    @Test
    public void mulDiv_mixed() {
        assertEquals(5, Evaluation.calculateMultiplyDivide(" 3+5 / 2 "));
    }

    @Test
    public void mulDiv_leftToRightAdditionSubtraction() {
        assertEquals(1, Evaluation.calculateMultiplyDivide("1-1+1"));
    }

    @Test
    public void mulDiv_multiDigit() {
        assertEquals(24, Evaluation.calculateMultiplyDivide("12+3*4"));
    }

    @Test
    public void mulDiv_continuousMultiplyDivide() {
        assertEquals(2, Evaluation.calculateMultiplyDivide("8/2/2"));
    }

    @Test
    public void mulDiv_singleNumber() {
        assertEquals(7, Evaluation.calculateMultiplyDivide("7"));
    }

    @Test
    public void mulDiv_empty() {
        assertEquals(0, Evaluation.calculateMultiplyDivide(""));
    }

    @Test
    public void mulDiv_complexExpression() {
        assertEquals(44, Evaluation.calculateMultiplyDivide("2+3*5+4/2+10*3-5"));
    }
}

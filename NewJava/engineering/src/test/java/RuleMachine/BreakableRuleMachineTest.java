package RuleMachine;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BreakableRuleMachineTest {

    @Test
    public void test1() {
        BreakableRuleMachine machine = new BreakableRuleMachine(true);

        machine.next(new NameRuleValidator(), "")
                .next(new AgeRuleValidator(), -1);

        List<RuleCheckResult> resultList = machine.validate();

        System.out.println("pause");
    }

    @Test
    public void test2() {
        BreakableRuleMachine machine = new BreakableRuleMachine(false);

        machine.next(new NameRuleValidator(), "")
                .next(new AgeRuleValidator(), -1);

        List<RuleCheckResult> resultList = machine.validate();

        System.out.println("pause");
    }

}
package engineering.rule;

import engineering.rule.main.BreakableRuleMachine;
import engineering.rule.testutils.RuleTestClass;
import engineering.rule.testutils.TestObject1;
import engineering.rule.testutils.TestObject2;
import engineering.rule.validator.TestRule1;
import engineering.rule.validator.TestRule2;

public class TestMain {

    public static void main(String[] args) {
        RuleTestClass ruleTestClass = new RuleTestClass();
        ruleTestClass.setTestObject1(new TestObject1("test"));
        ruleTestClass.setTestObject2(new TestObject2(2));

        BreakableRuleMachine breakableRuleMachine = new BreakableRuleMachine();
        breakableRuleMachine.next(TestRule1.INSTANCE, ruleTestClass.getTestObject1());
        breakableRuleMachine.next(TestRule2.INSTANCE, ruleTestClass.getTestObject2());

        breakableRuleMachine.validate();
    }
}

package engineering.rule.validator;

import engineering.rule.testutils.TestObject2;

import java.util.List;

public class TestRule2 implements RuleValidator<TestObject2> {

    public static final TestRule2 INSTANCE = new TestRule2();

    @Override
    public void validate(TestObject2 modelData, List<RuleResult> ruleResults) {
        if (modelData.getCode() == -1) {
            RuleResult ruleResult = new RuleResult();
            ruleResult.setCode(1);
            ruleResult.setMessage("test data");
            ruleResults.add(ruleResult);
        }
    }
}

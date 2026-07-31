package engineering.rule.validator;

import engineering.rule.testutils.TestObject1;
import lombok.Singular;

import java.util.List;

public class TestRule1 implements RuleValidator<TestObject1> {

    public static final TestRule1 INSTANCE = new TestRule1();

    @Override
    public void validate(TestObject1 modelData, List<RuleResult> ruleResults) {
        if (modelData.getName().equals("test")) {
            RuleResult ruleResult = new RuleResult();
            ruleResult.setCode(1);
            ruleResult.setMessage("test data");
            ruleResults.add(ruleResult);
        }
    }
}

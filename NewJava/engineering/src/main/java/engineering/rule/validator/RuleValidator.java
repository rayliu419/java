package engineering.rule.validator;

import java.util.List;

public interface RuleValidator<T> {

    void validate(T modelData, List<RuleResult> ruleResult);
}

package RuleMachine;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BreakableRuleMachine<T> {

    private List<Pair<RuleValidator<T>, T>> ruleValidatorTList;

    boolean breakable;

    public BreakableRuleMachine(boolean breakable) {
        this.breakable = breakable;
        this.ruleValidatorTList = new ArrayList<>();
    }

    BreakableRuleMachine next(RuleValidator<T> validator, T input) {
        this.ruleValidatorTList.add(Pair.of(validator, input));
        return this;
    }

    List<RuleCheckResult> validate() {
        List<RuleCheckResult> ruleCheckResults = new ArrayList<>();
        for (Pair<RuleValidator<T>, T> pair : ruleValidatorTList) {
            RuleValidator<T> validator = pair.getLeft();
            T input = pair.getRight();
            RuleCheckResult ruleCheckResult = validator.validate(input);
            ruleCheckResults.add(ruleCheckResult);
            if (ruleCheckResult.isPass()) {
                continue;
            }
            if (breakable == true) {
                break;
            }
        }
        return ruleCheckResults;
    }
}

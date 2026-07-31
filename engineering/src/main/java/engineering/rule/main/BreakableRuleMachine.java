package engineering.rule.main;

import engineering.rule.validator.RuleResult;
import engineering.rule.validator.RuleValidator;

import java.util.ArrayList;
import java.util.List;

public class BreakableRuleMachine<T> {

    private List<RuleValidator<T>> validators;

    private List<T> objects;

    private List<RuleResult> ruleResults;

    public BreakableRuleMachine() {
        validators = new ArrayList<>();
        objects = new ArrayList<>();
        ruleResults = new ArrayList<>();
    }

    public void next(RuleValidator<T> ruleValidator, T modelData) {
        validators.add(ruleValidator);
        objects.add(modelData);
    }

    public void validate() {
        for (int i = 0; i < validators.size(); i++) {
            RuleValidator<T> ruleValidator = validators.get(i);
            T object = objects.get(i);
            ruleValidator.validate(object, ruleResults);
            if (!ruleResults.isEmpty()) {
                System.out.println("find validate failures, exit");
                return;
            }
        }
    }

}

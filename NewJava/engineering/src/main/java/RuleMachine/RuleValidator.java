package RuleMachine;

public interface RuleValidator<T> {

    RuleCheckResult validate(T input);
}

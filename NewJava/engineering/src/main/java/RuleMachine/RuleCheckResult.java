package RuleMachine;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RuleCheckResult {

    private String checkInfo;

    private int code;

    boolean pass;

}

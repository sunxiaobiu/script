package HSO.model;

import java.util.*;

public class HSO {

    public String declareMethod;

    public String ifStatement;

    public SensitiveStmt sensitiveStmt;

    public TriggerConditionBlock triggerConditionBlock;

    public String triggerConditionStmt;

    public Set<String> variableInTrigger = new HashSet<>();

    public Set<String> variableInIfBranch = new HashSet<>();

    public Set<String> variableInElseBranch = new HashSet<>();

    public List<String> ifSensitive = new ArrayList<>();

    public List<String> elseSensitive = new ArrayList<>();

    public List<String> ifSensitiveOriginStmt = new ArrayList<>();

    public List<String> elseSensitiveOriginStmt = new ArrayList<>();

    public List<String> ifStmts = new ArrayList<>();

    public List<String> elseStmts = new ArrayList<>();

    public boolean ifHasDataDependency;

    public boolean elseHasDataDependency;

    public boolean hasCommonCaese = false;

    public boolean ifBranchIsHSO = false;

    public boolean elseBranchIsHSO = false;

    public boolean isHSO = false;

    public boolean branchDiff = true;

    public boolean triggerBranchHasSameAPIInIf = false;

    public boolean triggerBranchHasSameAPIInElse = false;

    public String file;

    public HSO(){
        this.sensitiveStmt = new SensitiveStmt();
        this.triggerConditionBlock = new TriggerConditionBlock();
    }

    public String coreAPIInTrigger;

}

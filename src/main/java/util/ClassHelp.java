package util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassHelp {

    public static boolean isTypeStateClass(String classComment) {
        return StringUtils.isNotBlank(classComment)
                && (classComment.contains("IllegalStateException")
        );
    }

    public static boolean containsIllegalStateException(String sentence) {
        return StringUtils.isNotBlank(sentence)
                && sentence.contains("IllegalStateException")
                && !sentence.contains("an invalid state")
        ;
    }

    public static boolean containsAnnotate(String classComment) {
        return classComment.contains("@throws")
                || classComment.contains("@param")
                || classComment.contains("@see")
                || classComment.contains("@return");
    }

    public static boolean isConditionalSentence(List<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return false;
        }
        List<String> res = new ArrayList<>();
        for (String token : tokens) {
            res.add(token.toLowerCase());
        }

        return res.contains("when")
                || res.contains("whether")
                || res.contains("if")
                || res.contains("assume")
//                || res.contains("before")
//                || res.contains("after")
                || res.contains("or")
                || res.contains("and")
                ;
    }

    public static List<Integer> conditionalTokenIndex(List<String> tokens) {
        List<Integer> res = new ArrayList<>();
        res.add(0);
        res.add(tokens.size() - 1);
        if (CollectionUtils.isEmpty(tokens)) {
            return res;
        }
        List<String> tokenLowerCases = new ArrayList<>();
        for (String token : tokens) {
            tokenLowerCases.add(token.toLowerCase());
        }

        if (tokenLowerCases.contains("when")) {
            res.add(tokenLowerCases.indexOf("when"));
        }
        if (tokenLowerCases.contains("whether")) {
            res.add(tokenLowerCases.indexOf("whether"));
        }
        if (tokenLowerCases.contains("if")) {
            res.add(tokenLowerCases.indexOf("if"));
        }
        if (tokenLowerCases.contains("assume")) {
            res.add(tokenLowerCases.indexOf("assume"));
        }
//        if (tokenLowerCases.contains("before")) {
//            res.add(tokenLowerCases.indexOf("before"));
//        }
//        if (tokenLowerCases.contains("after")) {
//            res.add(tokenLowerCases.indexOf("after"));
//        }
        if (tokenLowerCases.contains("or")) {
            res.add(tokenLowerCases.indexOf("or"));
        }
        if (tokenLowerCases.contains("and")) {
            res.add(tokenLowerCases.indexOf("and"));
        }
        if (tokenLowerCases.contains(".")) {
            res.add(tokenLowerCases.indexOf("."));
        }
        if (tokenLowerCases.contains(",")) {
            res.add(tokenLowerCases.indexOf(","));
        }

        List<Integer> sortedList = res.stream().distinct().sorted().collect(Collectors.toList());
        return sortedList;
    }

    public static List<Integer> INPosIndex(List<String> pos, List<Integer> clauseIndexList) {
//        if (CollectionUtils.isEmpty(pos)) {
//            return clauseIndexList;
//        }

//        for(int i=0; i < pos.size(); i++){
//            if (pos.get(i).equals("IN")) {
//                clauseIndexList.add(i);
//            }
//        }

        List<Integer> sortedList = clauseIndexList.stream().distinct().sorted().collect(Collectors.toList());
        return sortedList;
    }

}

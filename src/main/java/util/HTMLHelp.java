package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLHelp {
    /**
     * 匹配html标签，例如"<p>xxx</p>"这种格式
     */
    private static Pattern HTML_TAG_PATTERN = Pattern.compile("<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*?>");

    /**
     * 获取html中的数据
     * @param htmlString
     * @return
     */
    public static List<String> getResultsFromHtml(String htmlString) {
        List<String> results = new ArrayList<>();
        // 数据预处理
        htmlString = replaceStyle(removeBrTag(htmlString));
        if (htmlString != null && htmlString.length() > 0) {
            Matcher imageTagMatcher = HTML_TAG_PATTERN.matcher(htmlString);
            // 针对多个并列的标签的情况
            while (imageTagMatcher.find()) {
                String result = "";
                // group(1)对应正则表达式中的圆括号括起来的数据
                result = imageTagMatcher.group(1).trim();

                // 针对多个标签嵌套的情况进行处理
                if (result != null && result.length() > 0) {
                    result = replaceStartTag(result);
                }

                results.add(result);
            }
        }
        return results;
    }

    /**
     * 移除掉</br>标签
     *
     * @param src
     * @return
     */
    public static String removeBrTag(String src) {
        if (src != null && !src.isEmpty()) {
            src = src.replaceAll("<br/>", "");
        }
        return src;
    }

    /**
     * 替换掉html标签里面的style内容
     *
     * @param content
     * @return
     */
    public static String replaceStyle(String content) {
        if (content == null || content.length() == 0) {
            return content;
        }
        String regEx = " style=\"(.*?)\"";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        if (m.find()) {
            content = m.replaceAll("");
        }
        return content;
    }

    /**
     * 针对多个标签嵌套的情况进行处理
     * 比如 <p><span style="white-space: normal;">王者荣耀</span></p>
     * 预处理并且正则匹配完之后结果是 <span>王者荣耀
     * 需要手工移除掉前面的起始标签
     * @param content
     * @return
     */
    public static String replaceStartTag(String content) {
        if (content == null || content.length() == 0) {
            return content;
        }
        String regEx = "<[a-zA-Z]*?>([\\s\\S]*?)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        if (m.find()) {
            content = m.replaceAll("");
        }
        return content;
    }
}

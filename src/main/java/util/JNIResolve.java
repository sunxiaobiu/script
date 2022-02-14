package util;

import org.apache.commons.collections4.CollectionUtils;
import soot.Type;
import soot.coffi.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JNIResolve {

    public static String[] toTypeList(String s) {
        return listDesc(s).toArray(new String[0]);
    }

    public static List<String> listDesc(String desc) {
        List<String> list = new ArrayList(5);
        if (desc == null) {
            return list;
        }
        char[] chars = desc.toCharArray();
        int i = 0;
        while (i < chars.length) {
            switch (chars[i]) {
                case 'V':
                case 'Z':
                case 'C':
                case 'B':
                case 'S':
                case 'I':
                case 'F':
                case 'J':
                case 'D':
                    list.add(Character.toString(chars[i]));
                    i++;
                    break;
                case '[': {
                    int count = 1;
                    while (chars[i + count] == '[') {
                        count++;
                    }
                    if (chars[i + count] == 'L') {
                        count++;
                        while (chars[i + count] != ';') {
                            count++;
                        }
                    }
                    count++;
                    list.add(new String(chars, i, count));
                    i += count;
                    break;
                }
                case 'L': {
                    int count = 1;
                    while (chars[i + count] != ';') {
                        ++count;
                    }
                    count++;
                    list.add(new String(chars, i, count));
                    i += count;
                    break;
                }
                default:
                    throw new RuntimeException("can't parse type list: " + desc);
            }
        }
        return list;
    }

    /**
     *
     * @param desc
     *            a asm method desc, ex: (II)V
     * @return the desc of return type, ex: V
     */
    public static String getReturnTypeDesc(String desc) {
        int x = desc.lastIndexOf(')');
        if (x < 0) {
            throw new RuntimeException("not a validate Method Desc " + desc);
        }
        return desc.substring(x + 1);
    }

    public static Type extractMethodReturnType(String signature) {
        return Util.v().jimpleTypeOfFieldDescriptor(JNIResolve.getReturnTypeDesc(signature));
    }

    public static List<Type> extractMethodArgs(String signature) {
        int i = signature.indexOf('(');
        int j = signature.indexOf(')');
        String[] signatureList = JNIResolve.toTypeList(signature.substring(i+1, j));
        List<String> targetMethodArgs = Arrays.asList(signatureList);
        List<Type> sootMethodArgs = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(targetMethodArgs)){
            sootMethodArgs = targetMethodArgs.stream().map(arg ->{
                return Util.v().jimpleTypeOfFieldDescriptor(arg);
            }).collect(Collectors.toList());
        }
        return sootMethodArgs;
    }

}

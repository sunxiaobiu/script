package util;

import org.apache.commons.lang3.StringUtils;
import soot.SootClass;

public class ApplicationClassFilter {

    /**
     *
     * @param sootClass
     * @return
     */
    public static boolean isApplicationClass(SootClass sootClass){
        return isApplicationClass(sootClass.getPackageName());
    }

    /**
     *
     * @param sootClass
     * @return
     */
    public static boolean isApplicationClass(String clsName) {
        if (StringUtils.isBlank(clsName)) {
            return false;
        }
        if (clsName.startsWith("com.google.")
                || clsName.startsWith("soot.")
                || clsName.startsWith("android.")
                || clsName.startsWith("java.")
                || clsName.startsWith("com.facebook.")
                || clsName.startsWith("org.apache.")
                || clsName.startsWith("com.android.")
        ) {
            return false;
        }
        return true;
    }

    public static boolean isClassInSystemPackage(String className) {
        return className.startsWith("<android.") || className.startsWith("<java.") || className.startsWith("<javax.")
                || className.startsWith("<sun.") || className.startsWith("<org.omg.")
                || className.startsWith("<org.w3c.dom.") || className.startsWith("<com.google.")
                || className.startsWith("<com.android.") || className.startsWith("<org.apache.")
                || className.startsWith("<soot.")
                || className.startsWith("<androidx.")
                || className.startsWith("<com.android");
    }

    public static boolean isClassInAndroidPackage(String className) {
        return className.startsWith("<android.") || className.startsWith("<com.google.android")
                || className.startsWith("<com.android.")
                || className.startsWith("<androidx.");
    }

}
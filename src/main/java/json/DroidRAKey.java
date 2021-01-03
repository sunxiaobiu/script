package json;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sun on 2019/9/27.
 */
public class DroidRAKey {

    /**
     * refer to class name
     */
    public String className;

    /**
     *methodSignature
     */
    public String methodSignature;

    /**
     * type
     */
    public String type;

    /**
     * stmtSeq
     */
    public String stmtSeq;

    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof DroidRAKey)) {
            return false;
        }
        DroidRAKey other = (DroidRAKey) object;
        if (!StringUtils.equals(other.className, className)) {
            return false;
        }
        if (!StringUtils.equals(other.methodSignature, methodSignature)) {
            return false;
        }
        if (!StringUtils.equals(other.type, type)) {
            return false;
        }
        if (!StringUtils.equals(other.stmtSeq, stmtSeq)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.className, this.methodSignature, this.type, this.stmtSeq);
    }

    @Override
    public String toString() {
        return "json.DroidRAKey [className=" + className + ", methodSignature=" + methodSignature + ", type=" + type + ", stmtSeq=" + stmtSeq + "]";
    }
}

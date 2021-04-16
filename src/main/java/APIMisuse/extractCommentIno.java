package APIMisuse;

import com.github.javaparser.ParseException;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class extractCommentIno {

    public static void main(String[] args) throws FileNotFoundException, ParseException {

        String javaFilePath = args[0];

        Set<MethodSig> sigSet = new HashSet<MethodSig>();
        sigSet = JavaSourceVisitor.visit(javaFilePath);

        System.out.println("sigSet:" + sigSet);

    }
}

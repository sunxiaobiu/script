package util;

import soot.*;
import soot.javaToJimple.LocalGenerator;
import soot.jimple.Stmt;
import soot.options.Options;
import soot.util.Chain;

import java.util.*;

public class LoadApkWithSoot {

    public static Boolean run(String apkPath, String androidJar) {
        Set<SootClass> fragments = new HashSet<>();

        G.reset();

        String[] args =
                {
                        "-force-android-jar", androidJar,
                        "-process-dir", apkPath,
                        "-ire",
                        "-pp",
                        "-allow-phantom-refs",
                        "-w",
                        "-p", "cg", "enabled:false",
                };

        Options.v().set_src_prec(Options.src_prec_apk);
        Options.v().set_output_format(Options.output_format_none);
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.ExtractFragmentApk", new SceneTransformer() {

            @Override
            protected void internalTransform(String phaseName, Map<String, String> options) {
                Set<SootClass> dynamicFragment = new HashSet<>();
                Chain<SootClass> applicationClasses = Scene.v().getApplicationClasses();
                for (Iterator<SootClass> iter = applicationClasses.snapshotIterator(); iter.hasNext(); ) {
                    SootClass sootClass = iter.next();

                    // We copy the list of methods to emulate a snapshot iterator which
                    // doesn't exist for methods in Soot
                    List<SootMethod> methodCopyList = new ArrayList<>(sootClass.getMethods());
                    for (SootMethod sootMethod : methodCopyList) {
                        if (sootMethod.isConcrete()) {
                            final Body body = sootMethod.retrieveActiveBody();
                            final LocalGenerator lg = new LocalGenerator(body);

                            for (Iterator<Unit> unitIter = body.getUnits().snapshotIterator(); unitIter.hasNext(); ) {
                                Stmt stmt = (Stmt) unitIter.next();

                                if (stmt.containsInvokeExpr()) {
                                    SootMethod callee = stmt.getInvokeExpr().getMethod();

                                    // For Messenger.send(), we directly call the respective handler
//                                    if(null != callee && callee.getSignature().contains("Fragment")){
//                                        dynamicFragment.add(callee.getDeclaringClass());
//                                    }
                                    if (callee == Scene.v().grabMethod("<android.support.v4.app.FragmentTransaction: android.support.v4.app.FragmentTransaction add(int,android.support.v4.app.Fragment)>")) {

                                        SootClass fragmentClass = Scene.v().getSootClass(stmt.getInvokeExpr().getArgBox(1).getValue().getType().toString());
                                        System.out.println(callee);
                                        dynamicFragment.add(fragmentClass);
                                    }
                                }
                            }
                        }
                    }
                }
                fragments.addAll(dynamicFragment);
            }
        }));

        try {
            soot.Main.main(args);
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        if(fragments.size() != 0){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}

package org.dubok.classfile.signature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SignatureTree {

    final public SignatureTree parent;
    final public List<SignatureTree> children = new ArrayList<>();

    public SignatureTree() {
        this(null);
    }

    public SignatureTree(SignatureTree parent) {
        this.parent=parent;
    }

    public static void walk(SignatureTree signatureTree, SignatureVisitor signatureVisitor) {

        if(signatureTree.children.isEmpty()) { // terminal node
            SignatureTree.visit(signatureTree, signatureVisitor);
        } else { // non-terminal node
            SignatureTree.enter(signatureTree, signatureVisitor);
            for (SignatureTree x : signatureTree.children) {
                SignatureTree.walk(x, signatureVisitor);
            }
            SignatureTree.exit(signatureTree, signatureVisitor);
        }
    }

    public static void visit(SignatureTree signatureTree, SignatureVisitor signatureVisitor) {

        final String simpleName = signatureTree.getClass().getSimpleName();
        final String foo = "visit" + simpleName;

        Method[] methods = signatureVisitor.getClass().getMethods();
        for(Method method : methods) {
            if(method.getName().equals(foo)) {
                try {
                    method.invoke(signatureVisitor,signatureTree);
                    return;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        signatureVisitor.visit(signatureTree);
    }

    public static void enter(SignatureTree signatureTree, SignatureVisitor signatureVisitor) {

        final String simpleName = signatureTree.getClass().getSimpleName();
        final String foo = "enter" + simpleName;

        Method[] methods = signatureVisitor.getClass().getMethods();
        for(Method method : methods) {
            if(method.getName().equals(foo)) {
                try {
                    method.invoke(signatureVisitor,signatureTree);
                    return;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        signatureVisitor.enter(signatureTree);
    }

    public static void exit(SignatureTree signatureTree, SignatureVisitor signatureVisitor) {
        final String simpleName = signatureTree.getClass().getSimpleName();
        final String foo = "exit" + simpleName;

        Method[] methods = signatureVisitor.getClass().getMethods();
        for(Method method : methods) {
            if(method.getName().equals(foo)) {
                try {
                    method.invoke(signatureVisitor,signatureTree);
                    return;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        signatureVisitor.exit(signatureTree);
    }
}

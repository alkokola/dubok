package org.dubok.classfile.signature.tree;

import org.dubok.classfile.signature.SignatureTree;

public class ObjectType extends SignatureTree {
    public String type;
    public ObjectType(SignatureTree parent) {super(parent);}
}

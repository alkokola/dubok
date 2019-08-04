package org.dubok.classfile.signature.tree;

import org.dubok.classfile.signature.SignatureTree;

public class Identifier extends SignatureTree {
    public String identifier;
    public Identifier(SignatureTree parent) {super(parent);}
}

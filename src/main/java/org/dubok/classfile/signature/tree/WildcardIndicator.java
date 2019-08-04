package org.dubok.classfile.signature.tree;

import org.dubok.classfile.signature.SignatureTree;

public class WildcardIndicator extends SignatureTree {
    public String indicator;
    public WildcardIndicator(SignatureTree parent) {super(parent);}
}

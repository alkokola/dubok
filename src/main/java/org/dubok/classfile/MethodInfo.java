package org.dubok.classfile;

import org.dubok.classfile.attributes.Attribute;
import org.dubok.classfile.attributes.Signature;
import org.dubok.classfile.constantpool.Constant;
import org.dubok.classfile.constantpool.ConstantUtf8;
import org.dubok.classfile.signature.SignatureParser;
import org.dubok.Printable;
import org.dubok.classfile.signature.SignatureTree;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class MethodInfo implements Printable {

    // the description of the following fields and constants
    // can easily be found in Java Virtual Machine specification

    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_PRIVATE = 0x0002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_SYNCHRONIZED = 0x0020;
    public static final int ACC_BRIDGE = 0x0040;
    public static final int ACC_VARARGS = 0x0080;
    public static final int ACC_NATIVE = 0x0100;
    public static final int ACC_ABSTRACT = 0x0400;
    public static final int ACC_STRICT = 0x0800;
    public static final int ACC_SYNTHETIC = 0x1000;

    public final int accessFlags;
    public final int nameIndex;
    public final int descriptorIndex;
    public final Attribute[] attributes;
    public final Constant[] constantPool;

    // The Signature attibute or the field descriptor deserialized into syntax tree
    public final SignatureTree methodDescriptorOrSignature;

    private String getAccessFlags() {

        String flags = "";
        if ((accessFlags & ACC_PUBLIC) != 0)        flags += "|ACC_PUBLIC";
        if ((accessFlags & ACC_PRIVATE) != 0)       flags += "|ACC_PRIVATE";
        if ((accessFlags & ACC_PROTECTED) != 0)     flags += "|ACC_PROTECTED";
        if ((accessFlags & ACC_STATIC) != 0)        flags += "|ACC_STATIC";
        if ((accessFlags & ACC_FINAL) != 0)         flags += "|ACC_FINAL";
        if ((accessFlags & ACC_SYNCHRONIZED) != 0)  flags += "|ACC_SYNCHRONIZED";
        if ((accessFlags & ACC_BRIDGE) != 0)        flags += "|ACC_BRIDGE";
        if ((accessFlags & ACC_VARARGS) != 0)       flags += "|ACC_VARARGS";
        if ((accessFlags & ACC_NATIVE) != 0)        flags += "|ACC_NATIVE";
        if ((accessFlags & ACC_ABSTRACT) != 0)      flags += "|ACC_ABSTRACT";
        if ((accessFlags & ACC_STRICT) != 0)        flags += "|ACC_STRICT";
        if ((accessFlags & ACC_SYNTHETIC) != 0)     flags += "|ACC_SYNTHETIC";

        if(flags.length() != 0) flags = flags.trim().substring(1);
        return  flags;
    }

    public MethodInfo(Constant[] constantPool, final DataInput dataInput) throws IOException {
        this.constantPool = constantPool;
        accessFlags = dataInput.readUnsignedShort();
        nameIndex = dataInput.readUnsignedShort();
        descriptorIndex = dataInput.readUnsignedShort();
        attributes = new Attribute[dataInput.readUnsignedShort()];
        for(int i=0; i< attributes.length; i++) {
            attributes[i] = Attribute.newAttribute(constantPool, dataInput);
        }

        String signature = null;
        for(Attribute x : attributes) {
            if(x instanceof Signature) {
                signature = ((Signature)x).getSignature();
                break;
            }
        }
        if(signature == null) {
            SignatureParser signatureParser = new SignatureParser(((ConstantUtf8)constantPool[descriptorIndex]).utf8Value);
            methodDescriptorOrSignature = signatureParser.parseMethodDescriptor();
        } else {
            SignatureParser signatureParser = new SignatureParser(signature);
            methodDescriptorOrSignature = signatureParser.parseMethodSignature();
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "MethodInfo accFlags 0x"+ Integer.toString(accessFlags, 16)+" " + getAccessFlags() +" nameIndex #" + nameIndex + " desriptorIndex #" + descriptorIndex);
        indent += dumpIndent;
        constantPool[nameIndex].print(printStream, indent);
        constantPool[descriptorIndex].print(printStream, indent);
        for (Printable x : attributes) {
            x.print(printStream, indent + dumpIndent);
        }
    }
}

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

public class FieldInfo implements Printable {

    // the description of the following fields and constants
    // can easily be found in Java Virtual Machine specification

    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_PRIVATE = 0x0002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_VOLATILE = 0x0040;
    public static final int ACC_TRANSIENT = 0x0080;
    public static final int ACC_SYNTHETIC = 0x1000;
    public static final int ACC_ENUM = 0x4000;


    public final Constant[] constantPool;
    public final int accessFlags;
    public final int nameIndex;
    public final int descriptorIndex;
    public final Attribute[] attributes;

    // The Signature attibute or the field descriptor deserialized into syntax tree
    public final SignatureTree fieldDescriptorOrSignature;

    public FieldInfo(final Constant[] constantPool, final DataInput dataInput) throws IOException {
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
            fieldDescriptorOrSignature = signatureParser.parseFieldDescriptor();
        } else {
            SignatureParser signatureParser = new SignatureParser(signature);
            fieldDescriptorOrSignature = signatureParser.parseFieldSignature();
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {

        String flags = "";
        if(accessFlags == 0)                    flags += "|ACC_NONE";
        if( (accessFlags & ACC_PUBLIC) != 0)    flags += "|ACC_PUBLIC";
        if( (accessFlags & ACC_PRIVATE) != 0)   flags += "|ACC_PRIVATE";
        if( (accessFlags & ACC_PROTECTED) != 0) flags += "|ACC_PROTECTED";
        if( (accessFlags & ACC_STATIC) != 0)    flags += "|ACC_STATIC";
        if( (accessFlags & ACC_FINAL) != 0)     flags += "|ACC_FINAL";
        if( (accessFlags & ACC_VOLATILE) != 0)  flags += "|ACC_VOLATILE";
        if( (accessFlags & ACC_TRANSIENT) != 0) flags += "|ACC_TRANSIENT";
        if( (accessFlags & ACC_SYNTHETIC) != 0) flags += "|ACC_SYNTHETIC";
        if( (accessFlags & ACC_ENUM) != 0)      flags += "|ACC_ENUM";
        flags = flags.trim().substring(1);
        printStream.println(indent + "fieldInfo accessFlags = 0x" + Integer.toString(accessFlags, 16) +" " + flags + " nameIndex #" + nameIndex + " descriptorIndex #" + descriptorIndex);
        indent += Printable.dumpIndent;
        constantPool[nameIndex].print(printStream, indent);
        constantPool[descriptorIndex].print(printStream, indent);
        for (Attribute attribute: attributes) {
            attribute.print(printStream,indent);
        }
    }
}

package org.dubok.classfile;

import org.dubok.Printable;
import org.dubok.classfile.attributes.Attribute;
import org.dubok.classfile.attributes.Signature;
import org.dubok.classfile.constantpool.Constant;
import org.dubok.classfile.constantpool.ConstantDouble;
import org.dubok.classfile.constantpool.ConstantLong;
import org.dubok.classfile.constantpool.ConstantUtf8;
import org.dubok.classfile.constantpool.ConstantClass;

import org.dubok.classfile.signature.SignatureParser;

import org.dubok.classfile.signature.tree.ClassSignature;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class ClassFile implements Printable {

    // the description of the following fields and constants
    // can be found in Java Virtual Machine specification

    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_SUPER = 0x0020;
    public static final int ACC_INTERFACE = 0x0200;
    public static final int ACC_ABSTRACT = 0x0400;
    public static final int ACC_SYNTHETIC = 0x1000;
    public static final int ACC_ANNOTATION = 0x2000;
    public static final int ACC_ENUM = 0x4000;
    public static final int ACC_MODULE = 0x8000;


    private static final char[] hexDigits = "0123456789abcdef".toCharArray();

    private final byte[] magic;
    final public int minorVersion;
    final public int majorVersion;
    final public Constant[] constantPool;
    final public int accessFlags;
    final public int thisClass;
    final public int superClass;
    final public int[] interfaces;
    final public FieldInfo[] fields;
    final public MethodInfo[] methods;
    final public Attribute[] attributes;

    // It is convenient to have AST of the class signature here.
    // If the class does not have the Signature attribute,
    // the class signature is generated out of what the fields
    // "superClass" and "interfaces" reference in the constantPool

    public final ClassSignature classSignature;

    public ClassFile(final DataInput dataInput) throws IOException {
        magic = new byte[4];
        dataInput.readFully(magic);
        minorVersion = dataInput.readShort();
        majorVersion = dataInput.readShort();
        constantPool = new Constant[dataInput.readUnsignedShort()];

        for(int i = 1; i < constantPool.length; i++) {
            constantPool[i] = Constant.newConstant(i, constantPool, dataInput);
            if (constantPool[i] instanceof ConstantDouble | constantPool[i] instanceof ConstantLong) i++;
        }
        accessFlags = dataInput.readUnsignedShort();
        thisClass = dataInput.readUnsignedShort();
        superClass = dataInput.readUnsignedShort();
        interfaces = new int[dataInput.readUnsignedShort()];
        for(int i = 0; i< interfaces.length; i++ ) {
            interfaces[i] = dataInput.readUnsignedShort();
        }

        fields = new FieldInfo[dataInput.readUnsignedShort()];
        for (int i=0; i<fields.length; i++) {
            fields[i] = new FieldInfo(constantPool, dataInput);
        }

        methods = new MethodInfo[dataInput.readUnsignedShort()];
        for(int i=0; i< methods.length; i++) {
            methods[i] = new MethodInfo(constantPool, dataInput);
        }

        attributes = new Attribute[dataInput.readUnsignedShort()];
        for(int i=0; i< attributes.length; i++) {
            attributes[i] = Attribute.newAttribute(constantPool, dataInput);
        }

        // deserialize class signature into syntax tree
        String signature = null;
        for (Attribute x : attributes) {
            if(x instanceof Signature) {
                signature = ((Signature)x).getSignature();
                break;
            }
        }
        if(signature == null) {
            ConstantClass constantClass = ((ConstantClass)constantPool[superClass]);
            signature = "L" + ((ConstantUtf8)constantPool[constantClass.nameIndex]).utf8Value + ";";
            for(int x : interfaces) {
                constantClass = ((ConstantClass)constantPool[x]);
                signature += "L" + ((ConstantUtf8)constantPool[constantClass.nameIndex]).utf8Value + ";";
            }
        }
        SignatureParser signatureParser = new SignatureParser(signature);
        classSignature = signatureParser.parseClassSignature();

    }

    public String getAccessFlagsAsString(String indent) {
        String flags = "";
        if( (accessFlags & ACC_PUBLIC) != 0)     flags += "|ACC_PUBLIC";
        if( (accessFlags & ACC_FINAL) != 0)      flags += "|ACC_FINAL";
        if( (accessFlags & ACC_SUPER) != 0)      flags += "|ACC_SUPER";
        if( (accessFlags & ACC_INTERFACE) != 0)  flags += "|ACC_INTERFACE";
        if( (accessFlags & ACC_ABSTRACT) != 0)   flags += "|ACC_ABSTRACT";
        if( (accessFlags & ACC_SYNTHETIC) != 0)  flags += "|ACC_SYNTHETIC";
        if( (accessFlags & ACC_ANNOTATION) != 0) flags += "|ACC_ANNOTATION";
        if( (accessFlags & ACC_ENUM) != 0)       flags += "|ACC_ENUM";
        if( (accessFlags & ACC_MODULE) != 0)     flags += "|ACC_MODULE";
        if(flags.startsWith("|")) {
            return flags.trim().substring(1);
        } else {
            return flags.trim();
        }
    }

    public void print(final PrintStream printStream, String indent) {

        StringBuilder sb = new StringBuilder(magic.length * 3);
        for (int i = 0; i < magic.length; i++) {
            if(i!=0) {
                sb.append(":");
            }
            int k = magic[i] & 0xff;
            sb.append(hexDigits[k >>> 4]);
            sb.append(hexDigits[k & 0xf]);
        }
        printStream.println("magic     : " + sb.toString());
        printStream.println("minorVersion : " + minorVersion);
        printStream.println("majorVersion : " + majorVersion);

        printStream.println(getAccessFlagsAsString("accessFlags : "));
        constantPool[thisClass].print(printStream, "thisClass: ");

        if(superClass == 0) {
            printStream.println("superClass: index #0, this must be java.lang.Object"  );
        } else {
            constantPool[superClass].print(printStream, "superClass: ");
        }

        printStream.println("interfaces [" + interfaces.length + "]");
        for (int i =0; i< interfaces.length; i++ ) {
            constantPool[interfaces[i]].print(printStream, "interface " + i +": ");
        }

        printStream.println("fields [" + fields.length + "]");
        for(int i = 0; i< fields.length; i++) {
            fields[i].print(printStream, "field " + i + ": ");
        }

        printStream.println("methods [" + methods.length + "]");
        for(int i = 0; i< methods.length; i++) {
            methods[i].print(printStream, "method " + i + ": ");
        }

        printStream.println("attributes [" + attributes.length + "]");
        for(int i = 0; i< attributes.length; i++) {
            attributes[i].print(printStream, "attribute " + i + ": ");
        }
        printStream.println("constantPool [" + constantPool.length + "]");
        for(int i=1; i< constantPool.length; i++) {
            constantPool[i].print(printStream,  "");
            if (constantPool[i] instanceof ConstantDouble | constantPool[i] instanceof ConstantLong) {
                i++;
            }
        }
    }
}

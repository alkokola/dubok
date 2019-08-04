package org.dubok.classfile.constantpool;

import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public abstract class Constant implements Printable {

    private static final int CONSTANT_Class = 7;
    private static final int CONSTANT_Fieldref = 9;
    private static final int CONSTANT_Methodref = 10;
    private static final int CONSTANT_InterfaceMethodref = 11;
    private static final int CONSTANT_String = 8;
    private static final int CONSTANT_Integer = 3;
    private static final int CONSTANT_Float = 4;
    private static final int CONSTANT_Long = 5;
    private static final int CONSTANT_Double = 6;
    private static final int CONSTANT_NameAndType = 12;
    private static final int CONSTANT_Utf8 = 1;
    private static final int CONSTANT_MethodHandle = 15;
    private static final int CONSTANT_MethodType = 16;
    private static final int CONSTANT_InvokeDynamic = 18;
    private static final int CONSTANT_Module = 19;
    private static final int CONSTANT_Package = 20;

    final int index;
    final Constant[] constantPool;
    final int tag;

    Constant(int index, Constant[] constantPool, int tag) {
        this.index = index;
        this.constantPool = constantPool;
        this.tag = tag;
    }

    @Override
    abstract public void print(PrintStream printStream, String indent);

    public static Constant newConstant(int index, final Constant[] constantPool, final DataInput dataInput) throws IOException {

        int tag = dataInput.readByte();
        switch (tag) {

            case CONSTANT_Class: return new ConstantClass(index, constantPool, tag, dataInput);
            case CONSTANT_Fieldref: return new ConstantFieldref(index, constantPool, tag, dataInput);
            case CONSTANT_Methodref: return new ConstantMethodref(index,constantPool, tag, dataInput);
            case CONSTANT_InterfaceMethodref: return new ConstantInterfaceMethodref(index,constantPool, tag, dataInput);
            case CONSTANT_String: return new ConstantString(index,constantPool, tag, dataInput);
            case CONSTANT_Integer: return new ConstantInteger(index,constantPool, tag, dataInput);
            case CONSTANT_Float: return new ConstantFloat(index,constantPool, tag, dataInput);
            case CONSTANT_Long: return new ConstantLong(index,constantPool, tag, dataInput);
            case CONSTANT_Double: return new ConstantDouble(index,constantPool, tag, dataInput);
            case CONSTANT_NameAndType: return new ConstantNameAndType(index,constantPool, tag, dataInput);
            case CONSTANT_Utf8: return new ConstantUtf8(index,constantPool, tag, dataInput);
            case CONSTANT_MethodHandle: return new ConstantMethodHandle(index,constantPool, tag, dataInput);
            case CONSTANT_MethodType: return new ConstantMethodType(index,constantPool, tag,dataInput);
            case CONSTANT_InvokeDynamic: return new ConstantInvokeDynamic(index,constantPool, tag,dataInput);
            case CONSTANT_Module: return new ConstantModule(index,constantPool, tag,dataInput);
            case CONSTANT_Package: return new ConstantPackage(index,constantPool, tag,dataInput);
            default: {
                throw new ClassFormatError("Invalid constant pool tag: " + tag );
            }
        }
    }
}

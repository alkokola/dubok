package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantMethodHandle extends Constant {

    private static final int REF_getField = 1;
    private static final int REF_getStatic = 2;
    private static final int REF_putField = 3;
    private static final int REF_putStatic = 4;
    private static final int REF_invokeVirtual = 5;
    private static final int REF_invokeStatic = 6;
    private static final int REF_invokeSpecial = 7;
    private static final int REF_newInvokeSpecial = 8;
    private static final int REF_invokeInterface = 9;

    private int referenceKind;
    private int referenceIndex;

    private String getReferenceKindAsString() {
        switch (referenceKind) {
            case REF_getField: return "REF_getField (getfield C.f:T)";
            case REF_getStatic: return "REF_getStatic (getstatic C.f:T)";
            case REF_putField: return "REF_putField (putfield C.f:T)";
            case REF_putStatic: return "REF_putStatic (putstatic C.f:T)";
            case REF_invokeVirtual: return "REF_invokeVirtual ( invokevirtual C.m:(A*)T )";
            case REF_invokeStatic: return "REF_invokeStatic ( invokestatic C.m:(A*)T )";
            case REF_invokeSpecial: return "REF_invokeSpecial ( invokespecial C.m:(A*)T )";
            case REF_newInvokeSpecial: return "REF_newInvokeSpecial ( new C; dup; invokespecial C.<init>:(A*)V )";
            case REF_invokeInterface: return "REF_invokeInterface ( invokeinterface C.m:(A*)T ) ";
            default:
                return "Invalid reference kind " + referenceKind;
        }
    }

    ConstantMethodHandle(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        referenceKind = dataInput.readUnsignedByte();
        referenceIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + "(METHODHANDLE) referenceKind = " + referenceKind + " referenceIndex = " + referenceIndex + " " + getReferenceKindAsString());
        indent += dumpIndent;

        switch (referenceKind) {
            case REF_getField:
            case REF_getStatic:
            case REF_putField:
            case REF_putStatic:
                constantPool[referenceIndex].print(printStream, indent);
                break;
            case REF_invokeVirtual:
            case REF_newInvokeSpecial:
                constantPool[referenceIndex].print(printStream, indent);
                break;
            case REF_invokeStatic:
            case REF_invokeSpecial:
                constantPool[referenceIndex].print(printStream, indent);
                break;
            case REF_invokeInterface:
                constantPool[referenceIndex].print(printStream, indent);
                break;
            default:
                throw new ClassFormatError("Invalid referenceKind " + referenceKind);
        }

    }

}

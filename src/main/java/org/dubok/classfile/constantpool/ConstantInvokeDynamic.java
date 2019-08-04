package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantInvokeDynamic extends Constant {
    public final int bootstrapMethodAttrIndex;
    public final int nameAndTypeIndex;

    ConstantInvokeDynamic(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        bootstrapMethodAttrIndex = dataInput.readUnsignedShort();
        nameAndTypeIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (INVOKEDYNAMIC) bootstrapMethodAttrIndex #" + bootstrapMethodAttrIndex + " nameAndTypeIndex #" + nameAndTypeIndex);
        indent += dumpIndent;
        constantPool[nameAndTypeIndex].print(printStream, indent);
    }
}

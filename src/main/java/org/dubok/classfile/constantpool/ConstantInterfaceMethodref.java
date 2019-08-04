package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantInterfaceMethodref extends Constant {

    public final int classIndex;
    public final int nameAndTypeIndex;

    ConstantInterfaceMethodref(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        classIndex = dataInput.readUnsignedShort();
        nameAndTypeIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(final PrintStream printStream, String indent) {

        printStream.println(indent + "Constant #" + index + " (INRERFACEMETHODREF) classIndex #" + classIndex + " nameAndTypeIndex #" + nameAndTypeIndex);
        indent += dumpIndent;
        constantPool[classIndex].print(printStream, indent);
        constantPool[nameAndTypeIndex].print(printStream, indent);
    }
}

package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantFieldref extends Constant {
    public final int classIndex;
    public final int nameAndTypeIndex;

    ConstantFieldref(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index, constantPool, tag);
        classIndex = dataInput.readUnsignedShort();
        nameAndTypeIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (FIELDREF) classIndex #" + classIndex + " nameAndTypeIndex #" + nameAndTypeIndex );
        indent = indent + dumpIndent;
        constantPool[classIndex].print(printStream, indent);
        constantPool[nameAndTypeIndex].print(printStream, indent);
    }
}

package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantPackage extends Constant {

    public final int nameIndex;

    ConstantPackage(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        nameIndex = dataInput.readUnsignedShort();
    }
    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (PACKAGE) nameIndex #" + nameIndex);
        indent += dumpIndent;
        constantPool[nameIndex].print(printStream, indent);
    }
}

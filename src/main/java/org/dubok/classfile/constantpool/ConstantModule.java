package org.dubok.classfile.constantpool;


import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantModule extends Constant {
    public final int nameIndex;

    ConstantModule(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        nameIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println("Constant #" + index + " (MODULE) nameIndex #" + nameIndex);
        indent += dumpIndent;
        constantPool[nameIndex].print(printStream, indent);
    }
}

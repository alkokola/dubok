package org.dubok.classfile.constantpool;

import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantMethodType extends Constant {
    public final int descriptorIndex;

    ConstantMethodType(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        descriptorIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (METHODTYPE) descriptorIndex #" + descriptorIndex);
        indent += Printable.dumpIndent;
        constantPool[descriptorIndex].print(printStream, indent);
    }

}

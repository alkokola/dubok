package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantDouble extends Constant {
    public final double doubleValue;

    ConstantDouble(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        doubleValue = dataInput.readDouble();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (DOUBLE) doubleValue = " + doubleValue);
    }
}

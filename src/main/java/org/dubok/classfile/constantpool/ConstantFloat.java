package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantFloat extends Constant {
    public final float floatValue;

    ConstantFloat(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        floatValue = dataInput.readFloat();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (FLOAT) floatValue = " + floatValue);
    }
}

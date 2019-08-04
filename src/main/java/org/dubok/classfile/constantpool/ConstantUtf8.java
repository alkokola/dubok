package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantUtf8 extends Constant {

    public final String utf8Value;

    public ConstantUtf8(int index, Constant[] constantPool, int tag, DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        utf8Value = dataInput.readUTF();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (UTF8) utf8Value = " + utf8Value);
    }
}

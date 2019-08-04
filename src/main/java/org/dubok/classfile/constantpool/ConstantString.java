package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantString extends Constant {
    public final int stringIndex;

    public String getString() {
        return ((ConstantUtf8)constantPool[stringIndex]).utf8Value;
    }

    public ConstantString(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        stringIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, java.lang.String indent) {
        printStream.println( indent + "Constant #" + index + " (STRING) stringIndex #" + stringIndex);
        indent += dumpIndent;
        constantPool[stringIndex].print(printStream, indent);
    }
}

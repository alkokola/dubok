package org.dubok.classfile.constantpool;


import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantClass extends Constant {
    public final int nameIndex;
    public String getBinaryClassOrInterfaceName() {
        return ((ConstantUtf8) constantPool[nameIndex]).utf8Value;
    }

    ConstantClass(int index, Constant[] constantPool, int tag, DataInput dataInput) throws IOException{
        super(index, constantPool, tag);
        nameIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (CLASS) nameIndex #" + nameIndex);
        indent += Printable.dumpIndent;
        constantPool[nameIndex].print(printStream, indent);
    }
}

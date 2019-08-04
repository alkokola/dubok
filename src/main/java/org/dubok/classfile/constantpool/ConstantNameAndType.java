package org.dubok.classfile.constantpool;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantNameAndType extends Constant {

    public final int nameIndex;
    public final int descriptorIndex;

    ConstantNameAndType(int index, Constant[] constantPool, int tag, final DataInput dataInput) throws IOException {
        super(index,constantPool,tag);
        nameIndex = dataInput.readUnsignedShort();
        descriptorIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Constant #" + index + " (NAMETYPE) nameIndex #" + nameIndex + " descriptorIndex #" + descriptorIndex);
        indent += dumpIndent;
        constantPool[nameIndex].print(printStream, indent);
        constantPool[descriptorIndex].print(printStream, indent);
    }

}

package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class ConstantValue extends Attribute {

    public final int constantValueIndex;

    ConstantValue(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        constantValueIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (ConstantValue) attributeNameIndex = " + attributeNameIndex + " constantValueIndex = " + constantValueIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        constantPool[constantValueIndex].print(printStream, indent);
    }
}

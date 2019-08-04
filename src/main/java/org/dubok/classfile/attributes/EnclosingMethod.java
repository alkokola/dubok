package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class EnclosingMethod extends Attribute {

    private final int classIndex;
    private final int methodIndex;

    EnclosingMethod(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        classIndex = dataInput.readUnsignedShort();
        methodIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (EnclosingMethod) attributeNameIndex = " + attributeNameIndex + " classIndex = " + classIndex + " methodIndex = " + methodIndex );
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        constantPool[classIndex].print(printStream, indent);
        constantPool[methodIndex].print(printStream, indent);
    }
}

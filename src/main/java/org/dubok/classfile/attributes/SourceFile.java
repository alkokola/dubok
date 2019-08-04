package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.classfile.constantpool.ConstantUtf8;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class SourceFile extends Attribute {
    private final int sourceFileIndex;

    SourceFile(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        sourceFileIndex = dataInput.readUnsignedShort();
    }
    public String getSourceFile() {
        return ((ConstantUtf8)constantPool[sourceFileIndex]).utf8Value;
    }
    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (SourceFile) attributeNameIndex = " + attributeNameIndex + " sourceFileIndex = " + sourceFileIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        constantPool[sourceFileIndex].print(printStream, indent);
    }
}

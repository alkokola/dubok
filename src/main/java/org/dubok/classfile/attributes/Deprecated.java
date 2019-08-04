package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class Deprecated extends Attribute {

    Deprecated(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (Deprecated) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);

    }

}

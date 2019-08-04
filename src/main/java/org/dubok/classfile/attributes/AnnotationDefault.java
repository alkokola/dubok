package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class AnnotationDefault extends Attribute {
    final ElementValue elementValue;

    AnnotationDefault(Constant[] constantPool, int attributeNameIndex, DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        elementValue = new ElementValue(constantPool, dataInput);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (AnnotationDefault) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        elementValue.print(printStream, indent);
    }
}

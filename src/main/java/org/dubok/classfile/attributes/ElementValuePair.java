package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class ElementValuePair implements Printable {
    public final int  elementNameIndex;
    public final ElementValue elementValue;
    public final Constant[] constantPool;

    ElementValuePair(Constant[] constantPool, DataInput dataInput) throws IOException {
        this.constantPool = constantPool;
        elementNameIndex = dataInput.readUnsignedShort();
        elementValue = new ElementValue(constantPool, dataInput);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "elementValuePair elementNameIndex = " + elementNameIndex );
        indent += dumpIndent;
        constantPool[elementNameIndex].print(printStream, indent);
        elementValue.print(printStream, indent);
    }
}

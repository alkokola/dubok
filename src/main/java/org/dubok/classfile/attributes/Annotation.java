package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class Annotation implements Printable {
    public int annotationTypeIndex;
    public Constant[] constantPool;
    public ElementValuePair elementValuePairs[];
    protected Annotation(){
        constantPool = null;
        elementValuePairs = null;
    }

    Annotation(Constant[] constantPool, DataInput dataInput) throws IOException {
        this.constantPool = constantPool;
        annotationTypeIndex = dataInput.readUnsignedShort();
        elementValuePairs = new ElementValuePair[dataInput.readUnsignedShort()];
        for (int i=0; i< elementValuePairs.length; i++) {
            elementValuePairs[i] = new ElementValuePair(constantPool, dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "annotation annotationTypeIndex = " + annotationTypeIndex);
        indent += dumpIndent;
        constantPool[annotationTypeIndex].print(printStream,indent);
        printStream.println(indent + "elementValuePairs [" + elementValuePairs.length + "]" );
        indent += dumpIndent;
        for(Printable x : elementValuePairs) {
            x.print(printStream, indent);
        }
    }
}

package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class RuntimeInvisibleAnnotations extends Attribute {

    public Annotation[] annotations;

    RuntimeInvisibleAnnotations(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        annotations = new Annotation[dataInput.readUnsignedShort()];
        for(int i = 0; i< annotations.length; i++ ) {
            annotations[i] = new Annotation(constantPool, dataInput);
        }
    }
    @Override
    public void print(PrintStream printStream, String indent)  {
        printStream.println(indent + "Attribute (RuntimeInvisibleAnnotations) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "annotations [" + annotations.length + "]" );
        indent += dumpIndent;
        for (Printable x : annotations) {
            x.print(printStream, indent);
        }
    }

}

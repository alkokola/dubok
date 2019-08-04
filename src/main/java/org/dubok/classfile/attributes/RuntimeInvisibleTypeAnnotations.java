package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class RuntimeInvisibleTypeAnnotations extends Attribute {

    public final TypeAnnotation[] annotations;

    RuntimeInvisibleTypeAnnotations(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        annotations = new TypeAnnotation[dataInput.readUnsignedShort()];
        for(int i=0; i< annotations.length; i++) {
            annotations[i] = new TypeAnnotation(constantPool, dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent)  {
        printStream.println(indent + "Attribute (RuntimeInvisibleTypeAnnotations) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "typeAnnotations [" + annotations.length + "]" );
        indent += dumpIndent;
        for(TypeAnnotation typeAnnotation: annotations) {
            typeAnnotation.print(printStream, indent);
        }
    }

}

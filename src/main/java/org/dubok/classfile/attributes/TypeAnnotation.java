package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class TypeAnnotation extends Annotation implements Printable {

    public final int targetType;
    public final TargetInfo targetInfo;
    public final Path typePaths[];


    TypeAnnotation(Constant[] constantPool, DataInput dataInput) throws IOException {
        this.constantPool = constantPool;
        targetType = dataInput.readUnsignedByte();
        targetInfo = new TargetInfo(targetType, dataInput);
        typePaths = new Path[dataInput.readUnsignedByte()];
        for (int i=0; i< typePaths.length;i++) {
            typePaths[i] = new Path(dataInput);
        }
        annotationTypeIndex = dataInput.readUnsignedShort();
        elementValuePairs = new ElementValuePair[dataInput.readUnsignedShort()];
        for (int i=0; i< elementValuePairs.length; i++) {
            elementValuePairs[i] = new ElementValuePair(constantPool, dataInput);
        }

    }
    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "typeAnnotation annotationTypeIndex  = " + annotationTypeIndex+ " targetType = " + targetType);
        indent += dumpIndent;
        constantPool[annotationTypeIndex].print(printStream,indent);
        targetInfo.print(printStream, indent);
        printStream.println(indent + "typePaths [" + typePaths.length + "]");
        for(Path path : typePaths) {
            path.print(printStream, indent + dumpIndent);
        }
        printStream.println(indent + "elementValuePairs [" + elementValuePairs.length + "]" );
        indent += dumpIndent;
        for(ElementValuePair x : elementValuePairs) {
            x.print(printStream, indent);
        }
    }
}

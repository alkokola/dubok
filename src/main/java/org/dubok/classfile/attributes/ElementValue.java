package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class ElementValue implements Printable {

    public final char valueType;
    public int constValueIndex;           // valueType in B C D F I J S Z s
    public int typeNameIndex;             // valueType = e
    public int constNameIndex;            // valueType = e
    public int classInfoIndex;            // valueType = c
    public ElementValue elementValues[];  // valueType = [
    public Annotation annotation;         // valueType = @
    public final Constant[] constantPool;

    ElementValue(Constant[] constantPool, DataInput dataInput) throws IOException {
        this.constantPool = constantPool;
        valueType = (char) dataInput.readUnsignedByte();

        switch (valueType) {
            case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'S': case 'Z': case 's':
                constValueIndex = dataInput.readUnsignedShort();
                break;
            case 'c':
                classInfoIndex = dataInput.readUnsignedShort();
                break;
            case 'e':
                typeNameIndex = dataInput.readUnsignedShort();
                constNameIndex = dataInput.readUnsignedShort();
                break;
            case '@':
                annotation = new Annotation(constantPool, dataInput);
                break;
            case '[':
                elementValues = new ElementValue[dataInput.readUnsignedShort()];
                for (int i=0; i< elementValues.length; i++) {
                    elementValues[i] = new ElementValue(constantPool, dataInput);
                }
                break;
            default:
                throw new ClassFormatError("Invalid type : " + valueType);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "elementValue  valueType = " + valueType);
        indent += dumpIndent;
        switch (valueType) {
            case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'S': case 'Z': case 's':
                constantPool[constValueIndex].print(printStream, indent);
                break;
            case 'c':
                constantPool[classInfoIndex].print(printStream, indent);
                break;
            case 'e':
                constantPool[typeNameIndex].print(printStream, indent);
                constantPool[constNameIndex].print(printStream, indent);
                break;
            case '@':
                annotation.print(printStream, indent);
                break;
            case '[':
                printStream.println(indent + "elementValues [" + elementValues.length + "]");
                indent += dumpIndent;
                for(ElementValue x : elementValues) {
                    x.print(printStream, indent);
                }
                break;
            default:
                throw new ClassFormatError("Invalid type : " + valueType);
        }
    }
}

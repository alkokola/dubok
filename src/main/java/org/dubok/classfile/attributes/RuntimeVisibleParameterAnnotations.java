package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class RuntimeVisibleParameterAnnotations extends Attribute {

    class ElementValuePair implements  Printable{
        final int  elementNameIndex;
        ElementValue elementValue;
        ElementValuePair(DataInput dataInput) throws IOException {
            elementNameIndex = dataInput.readUnsignedShort();
            elementValue = new ElementValue(dataInput);
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "elementValuePair elementNameIndex = " + elementNameIndex );
            indent += dumpIndent;
            constantPool[elementNameIndex].print(printStream, indent);
            elementValue.print(printStream, indent);
        }
    }

    class ElementValue implements Printable {
        char valueType;
        int  valueIndex;               // valueType in B C D F I J S Z s constValueIndex, c classInfoIndex, e enumTypeNameIndex
        int  enumTypeNameIndex;        // valueType = e enumTypeNameIndex
        ElementValue elementValues[];  // valueType = [
        Annotation annotation;         // valueType = @

        ElementValue(DataInput dataInput) throws IOException {
            valueType = (char) dataInput.readUnsignedByte();
            switch (valueType) {
                case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'S': case 'Z': case 's': case 'c':
                    valueIndex = dataInput.readUnsignedShort();
                    break;
                case 'e':
                    valueIndex = dataInput.readUnsignedShort();
                    enumTypeNameIndex = dataInput.readUnsignedShort();
                    break;
                case '@':
                    annotation = new Annotation(dataInput);
                    break;
                case '[':
                    elementValues = new ElementValue[dataInput.readUnsignedShort()];
                    for (int i=0; i< elementValues.length; i++) {
                        elementValues[i] = new ElementValue(dataInput);
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
                case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'S': case 'Z': case 's': case 'c':
                    constantPool[valueIndex].print(printStream, indent);
                    break;
                case 'e':
                    constantPool[valueIndex].print(printStream, indent);
                    constantPool[enumTypeNameIndex].print(printStream, indent);
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

    class Annotation implements Printable {
        int annotationTypeIndex;
        ElementValuePair elementValuePairs[];

        Annotation(DataInput dataInput) throws IOException {
            annotationTypeIndex = dataInput.readUnsignedShort();
            elementValuePairs = new ElementValuePair[dataInput.readUnsignedShort()];
            for (int i=0; i< elementValuePairs.length; i++) {
                elementValuePairs[i] = new ElementValuePair(dataInput);
            }
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "annotation annotationTypeIndex = " + annotationTypeIndex);
            indent += dumpIndent;
            constantPool[annotationTypeIndex].print(printStream,indent);
            printStream.println(indent + "elementValuePairs [" + elementValuePairs.length + "]" );
            indent += dumpIndent;
            for(ElementValuePair x : elementValuePairs) {
                x.print(printStream, indent);
            }
        }
    }

    private Annotation[][] annotations; // num_parameters x num_annotations

    RuntimeVisibleParameterAnnotations(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput ) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());

        annotations = new Annotation[dataInput.readUnsignedByte()][]; // num_parameters

        for(int i = 0; i< annotations.length; i++ ) {
            annotations[i] = new Annotation[dataInput.readUnsignedShort()]; // num_annotations
            for(int j=0; j < annotations[i].length; j++) {
                annotations[i][j] = new Annotation(dataInput);
            }
        }

    }

    @Override
    public void print(PrintStream printStream, String indent)  {
        printStream.println(indent + "Attribute (RuntimeVisibleParameterAnnotations) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "annotations [" + annotations.length + "][]  // for number of parameters" );
        indent +=dumpIndent;
        for (Printable[] y : annotations) {
            printStream.println(indent + "annotations [" + y.length + "]  // for number of annotations of the parameter" );
            for(Printable x : y) {
                x.print(printStream, indent + dumpIndent);
            }
        }
    }
}

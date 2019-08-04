package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class LocalVariableTypeTable extends Attribute {

    class LocalVariableType implements Printable {
        final int startPc;
        final int length;
        final int nameIndex;
        final int signatureIndex;
        final int index;

        LocalVariableType(final DataInput dataInput) throws IOException {
            startPc = dataInput.readUnsignedShort();
            length = dataInput.readUnsignedShort();
            nameIndex = dataInput.readUnsignedShort();
            signatureIndex = dataInput.readUnsignedShort();
            index = dataInput.readUnsignedShort();
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "localVariableType startPc = " + startPc + " length = " + length + " nameIndex = " + nameIndex + " signatureIndex = " + signatureIndex + " index = " + index);
            constantPool[nameIndex].print(printStream, indent + "  ");
            constantPool[signatureIndex].print(printStream, indent + "  ");
        }
    }
    private LocalVariableType[] localVariableTypes;

    LocalVariableTypeTable(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        localVariableTypes = new LocalVariableType[dataInput.readUnsignedShort()];
        for(int i =0; i< localVariableTypes.length; i++) {
            localVariableTypes[i] = new LocalVariableType(dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (LocalVariableTypeTable) attributeNameIndex = " + attributeNameIndex );
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "localVariableTypes [" + localVariableTypes.length + "]");
        for (Printable x : localVariableTypes) {
            x.print(printStream, indent + dumpIndent);
        }
    }
}

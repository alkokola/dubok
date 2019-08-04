package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class LocalVariableTable extends Attribute {

    class LocalVariable implements Printable {
        final int startPc;
        final int length;
        final int nameIndex;
        final int descriptorIndex;
        final int index;

        LocalVariable(final DataInput dataInput) throws IOException {
            startPc = dataInput.readUnsignedShort();
            length = dataInput.readUnsignedShort();
            nameIndex = dataInput.readUnsignedShort();
            descriptorIndex = dataInput.readUnsignedShort();
            index = dataInput.readUnsignedShort();
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "localVariable startPc = " + startPc + " length = " + length + " nameIndex = " + nameIndex + " descriptorIndex = " + descriptorIndex + " index = " + index);
            indent += dumpIndent;
            constantPool[nameIndex].print(printStream, indent);
            constantPool[descriptorIndex].print(printStream, indent);
        }

    }
    private LocalVariable[] localVariableTable;

    LocalVariableTable(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        localVariableTable = new LocalVariable[dataInput.readUnsignedShort()];
        for(int i =0; i< localVariableTable.length; i++) {
            localVariableTable[i] = new LocalVariable(dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (LocalVariableTable) attributeNameIndex = " + attributeNameIndex );
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "localVariables [" + localVariableTable.length + "]");
        indent += dumpIndent;
        for (Printable x : localVariableTable) {
            x.print(printStream, indent);
        }
    }
}

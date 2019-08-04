package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class Exceptions extends Attribute {

    private int[] exceptionIndexTable;

    Exceptions(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        exceptionIndexTable = new int[dataInput.readUnsignedShort()];
        for(int i=0; i< exceptionIndexTable.length; i++) {
            exceptionIndexTable[i] = dataInput.readUnsignedShort();
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (Exceptions) attributeNameIndex " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "exceptionIndexTable [" + exceptionIndexTable.length + "]" ) ;
        indent += dumpIndent;
        for (int i : exceptionIndexTable) {
            constantPool[i].print(printStream, indent );
        }
    }
}

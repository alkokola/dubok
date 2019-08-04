package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class LineNumberTable extends Attribute {

    class LineNumber implements Printable {
        int startPc;
        int lineNumber;

        LineNumber(final DataInput dataInput) throws  IOException{
            startPc = dataInput.readUnsignedShort();
            lineNumber = dataInput.readUnsignedShort();
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "lineNumber startPc = " + startPc + " lineNumber = " + lineNumber);
        }
    }

    private LineNumber[] lineNumberTable;

    LineNumberTable(final Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        lineNumberTable = new LineNumber[dataInput.readUnsignedShort()];
        for (int i = 0; i< lineNumberTable.length; i++) {
            lineNumberTable[i] = new LineNumber(dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (LineNumberTable) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "lineNumbers [" + lineNumberTable.length + "]");
        for(Printable x : lineNumberTable) {
            x.print(printStream, indent + dumpIndent);
        }
    }
}
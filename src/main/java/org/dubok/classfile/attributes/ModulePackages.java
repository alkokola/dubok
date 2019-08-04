package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class ModulePackages extends Attribute {
    private int[] packageIndices;

    ModulePackages(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        packageIndices = new int[dataInput.readUnsignedShort()];
        for(int i=0; i< packageIndices.length; i++) {
            packageIndices[i] = dataInput.readUnsignedShort();
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (ModulePackages) attributeNameIndex " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent + dumpIndent);
        printStream.println(indent + "packageIndices [" + packageIndices.length + "]" );
        for(int i : packageIndices) {
            constantPool[i].print(printStream, indent + dumpIndent);
        }
    }
}

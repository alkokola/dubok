package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class BootstrapMethods extends Attribute {

    class BootstrapMethod implements Printable {
        int bootstrapMethodRef;
        int bootstrapArguments[]; //indices in the constant pool
        BootstrapMethod(DataInput dataInput) throws IOException {
            bootstrapMethodRef = dataInput.readUnsignedShort();
            bootstrapArguments = new int[dataInput.readUnsignedShort()];
            for(int i = 0; i < bootstrapArguments.length; i++) {
                bootstrapArguments[i] = dataInput.readUnsignedShort();
            }
        }

        @Override
        public void print(final PrintStream printStream, String indent) {
            printStream.println(indent + "bootstrapMethod" );
            indent = indent + "  ";
            constantPool[bootstrapMethodRef].print(printStream, indent);
            printStream.println(indent + "bootstrapArguments [" + bootstrapArguments.length + "]" );
            for(int i : bootstrapArguments) {
                constantPool[i].print(printStream, indent + dumpIndent);
            }
        }
    }

    private BootstrapMethod[] bootstrapMethods;

    BootstrapMethods(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        bootstrapMethods = new BootstrapMethod[dataInput.readUnsignedShort()];
        for(int i =0; i<bootstrapMethods.length; i++) {
            bootstrapMethods[i] = new BootstrapMethod(dataInput);
        }
    }

    @Override
    public void print(final PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (BootstrapMethods) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "bootstrapMethodsTable [" + bootstrapMethods.length + "]" );
        indent += dumpIndent;
        for( Printable x: bootstrapMethods) {
            x.print(printStream, indent);
        }
    }
}

package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.classfile.instructions.Instruction;
import org.dubok.Printable;
//import org.dubok.bytecode.Bytecode;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class Code extends Attribute {

    public final int maxStacks;
    public final int maxLocals;
    public final List<Instruction> bytecode;

    class ExceptionTableEntry implements Printable {
        int startPc;
        int endPc;
        int handlerPc;
        int catchType;
        ExceptionTableEntry(final DataInput dataInput) throws IOException {
            startPc = dataInput.readUnsignedShort();
            endPc = dataInput.readUnsignedShort();
            handlerPc = dataInput.readUnsignedShort();
            catchType = dataInput.readUnsignedShort();
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "exception startPc = " + startPc + " endPc = " + endPc + " handlerPc = " + handlerPc + " catchType = " + catchType);
        }
    }

    private ExceptionTableEntry[] exceptionTable;
    private Attribute[] attributes;

    Code(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        maxStacks = dataInput.readUnsignedShort();
        maxLocals = dataInput.readUnsignedShort();
        int length = dataInput.readInt();
        bytecode = Instruction.getBytecode(constantPool,length,dataInput);
        exceptionTable = new ExceptionTableEntry[dataInput.readUnsignedShort()];

        for(int i=0; i< exceptionTable.length; i++) {
            exceptionTable[i] = new ExceptionTableEntry(dataInput);
        }
        attributes = new Attribute[dataInput.readUnsignedShort()];

        for (int i=0; i<attributes.length; i++) {
            attributes[i] = Attribute.newAttribute(constantPool, dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {

        printStream.println(indent + "Attribute (Code) attributeNameIndex #" + attributeNameIndex + " maxStacks = " + maxStacks + " maxLocals = " + maxLocals) ;
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);

        for (Printable x : bytecode) {
            x.print(printStream, indent);
        }

        printStream.println(indent + "exceptions [" + exceptionTable.length + "]");
        for (Printable x : exceptionTable) {
            x.print(printStream, indent + dumpIndent);
        }

        printStream.println(indent + "attributes [" + attributes.length + "]");
        for (Printable x : attributes) {
            x.print(printStream, indent + dumpIndent);
        }
    }
}

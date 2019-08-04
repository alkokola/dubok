package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class invokeinterface extends Instruction{

    public final int index;
    public final int count;
    public String extraComment;

    public invokeinterface(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 185;
        index = dataInput.readUnsignedShort();
        count = dataInput.readUnsignedByte();
        dataInput.readByte(); // skip one byte

        length = 5;
        comment = "Operand Stack: ..., objectref, [arg1, [arg2 ...]] -> ...";
        extraComment = "index in the  constant pool is #" + index + ", count = " + count;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
        constantPool[index].print(printStream, indent + getIndent() + "// ");
    }

}


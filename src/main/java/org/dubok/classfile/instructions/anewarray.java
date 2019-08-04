package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public final class anewarray extends Instruction {
    public final int index;
    public String extraComment;
    public anewarray(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 189;
        length = 3;
        int index1 = dataInput.readUnsignedByte();
        int index2 = dataInput.readUnsignedByte();
        index = (index1 << 8) | index2;

        comment = "Operand Stack: ..., count -> ..., arrayref";
        extraComment = "The type of array is taken from constant pool at index #" + index;
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
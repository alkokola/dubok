package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public final class bipush extends Instruction {

    public final int byteValue;
    public String extraComment;

    public bipush(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 16;
        length = 2;
        comment = "Operand Stack: ... -> ..., value";
        byteValue = dataInput.readUnsignedByte();
        extraComment = "The immediate byte value is " + (byte)byteValue;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }

}

package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class dload extends Instruction{

    public final int index;
    public String extraComment;

    public dload(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 24;
        index = dataInput.readUnsignedByte();
        length = 2;
        comment = "Operand Stack: ... -> ..., value";
        extraComment = "value is taken from local variable #" + index + " and #" + (index + 1);
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}

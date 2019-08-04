package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class dstore extends Instruction {
    public final int index;
    String extraComment;
    public dstore(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 57;
        length = 2;
        comment = "Operand Stack: ..., value -> ...";
        index = dataInput.readUnsignedByte();
        extraComment = "value is stored into local variables #" + index + " and #" + (index + 1);
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}

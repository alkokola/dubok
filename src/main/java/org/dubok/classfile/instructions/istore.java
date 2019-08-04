package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class istore  extends Instruction {
    public final int index;
    String extraComment;
    public istore(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 54;
        comment = "Operand Stack: ..., value -> ...";
        index = dataInput.readUnsignedByte();
        extraComment = "value is stored into local variables #" + index;
        maxMnemonicLength = mmemonic.length();
        length= 2;
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}

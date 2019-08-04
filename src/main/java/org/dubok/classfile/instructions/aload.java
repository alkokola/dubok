package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;
import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;


public final class aload extends Instruction {
    public final int index;
    public final String extraComment;
    public aload(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 25;
        index = dataInput.readUnsignedByte();
        length = 2;
        comment = "Operand Stack: ... -> objectref";
        extraComment = "objectref is taken from local variable #" + index;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }

}

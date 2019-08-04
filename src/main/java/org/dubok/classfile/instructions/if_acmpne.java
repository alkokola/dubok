package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class if_acmpne extends Instruction {

    public final int branchOffset;
    public String branchLabel;

    public String extraComment;

    public if_acmpne(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert opcode == 166;
        length = 3;
        branchOffset = dataInput.readShort();
        comment = "Operand Stack: ..., value1, value2 -> ...";
        extraComment = "Branch if not equals, branchOffset = " + branchOffset + ", branch to instruction at offset " + (offset + branchOffset);
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}
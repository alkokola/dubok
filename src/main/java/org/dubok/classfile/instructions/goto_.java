package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class goto_ extends Instruction {

    public final int branchOffset;
    public String branchLabel;
    public String extraComment;

    public goto_(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert opcode == 167;
        length = 3;
        branchOffset = dataInput.readShort();
        comment = "Operand Stack: No change";
        extraComment = "Branch always  branchOffset = " + branchOffset + ", branch to instruction at offset " + (offset + branchOffset);
        mmemonic = "goto";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}


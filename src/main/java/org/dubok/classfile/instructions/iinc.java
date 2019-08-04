package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class iinc extends Instruction {

    public final int index;
    public final int _const;

    public String extraComment;

    public iinc(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert opcode == 132;
        length = 3;
        index = dataInput.readUnsignedByte();
        _const = dataInput.readByte();
        comment = "Operand Stack: No change";
        extraComment = "The local variable at index #" + index + " is incremented by " + _const;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}


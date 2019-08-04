package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class multianewarray extends Instruction {
    public final int index;
    public final int dimensions;
    String extraComment;
    public multianewarray(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 197;
        length = 4;
        comment = "Operand Stack: ...,  count1, [count2, ...] -> ..., arrayref";
        index = dataInput.readUnsignedShort();
        dimensions = dataInput.readUnsignedByte();
        extraComment = "dimensions = " + dimensions +", type is taken from constant pool at index #" + index;
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


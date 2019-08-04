package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class ldc extends Instruction {

    public final int index;

    public ldc(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert opcode == 18;
        length = 2;
        index = dataInput.readUnsignedByte();
        comment = "Operand Stack: ... -> ..., value from constant pool at index #"  + index;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        constantPool[index].print(printStream, indent + getIndent() + "// ");
    }
}


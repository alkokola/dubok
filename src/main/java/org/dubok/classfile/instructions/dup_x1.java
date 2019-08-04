package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public final class dup_x1 extends Instruction {
    public dup_x1(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 90;
        comment =  "Operand Stack: ..., value2, value1 -> ..., value1, value2, value1";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

}

package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class dup2 extends Instruction {
    public dup2(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 92;
        comment =  "Operand Stack: ..., ..., value2, value1 -> ..., value2, value1, value2, value1";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

}

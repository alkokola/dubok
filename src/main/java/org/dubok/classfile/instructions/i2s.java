package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class i2s  extends Instruction {
    public i2s (int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 147;
        comment =  "Operand Stack: ..., value -> ..., result";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

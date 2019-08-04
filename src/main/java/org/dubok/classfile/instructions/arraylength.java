package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public final class arraylength extends Instruction {
    public arraylength(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 190;
        comment = "Operand Stack: ..., arrayref -> ..., length";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}
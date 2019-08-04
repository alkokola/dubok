package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public final class aconst_null extends Instruction {

    public aconst_null(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 1;
        comment =  "Operand Stack: ... -> null";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

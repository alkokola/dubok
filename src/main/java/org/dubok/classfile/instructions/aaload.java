package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.IOException;
import java.util.List;

public final class aaload extends Instruction {

    public aaload(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 50;
        comment = "Operand Stack: ..., arrayref, index -> ..., value";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

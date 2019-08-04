package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public final class athrow extends Instruction {

    public athrow(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 191;
        comment =  ".., objectref -> objectref";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

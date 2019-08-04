package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;


public final class daload extends Instruction {
    public daload(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 49;
        comment =  "Operand Stack: ..., arrayref, index -> ..., value";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

}

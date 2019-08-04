package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class l2i  extends Instruction {
    public l2i (int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 136;
        comment =  "Operand Stack: ..., value -> ..., result";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}
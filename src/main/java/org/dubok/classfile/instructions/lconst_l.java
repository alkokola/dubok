package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class lconst_l extends Instruction{
    public lconst_l(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert (opcode >= 9) && (opcode <= 10);
        comment = "Operand Stack: ... -> ..., " + (opcode - 9);
        mmemonic = "lconst_" + (opcode - 9);
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}
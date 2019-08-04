package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class iconst_i extends Instruction{
    public iconst_i(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert (opcode >= 2) && (opcode <= 8);
        comment = "Operand Stack: ... -> ..., " + (opcode - 3);
        if (opcode == 2) {
            mmemonic = "iconst_m1";
        } else {
            mmemonic = "iconst_" + (opcode - 3);
        }
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}
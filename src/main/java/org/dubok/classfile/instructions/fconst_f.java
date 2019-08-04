package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class fconst_f extends Instruction{
    public fconst_f(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert (opcode >= 11) && (opcode <= 13);
        comment = "Operand Stack: ... -> ..., " + (float) (opcode - 11);
        mmemonic = "fconst_" + (opcode - 11);
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class dconst_d extends Instruction {
    public dconst_d(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 14 || opcode == 15;
        length = 1;
        comment =  "Operand Stack: ...-> ..., " + (opcode - 14);
        mmemonic = "dconst_" + (opcode - 14) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

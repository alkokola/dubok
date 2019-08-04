package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class fload_n extends Instruction {

    public fload_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert (opcode >= 34) && (opcode <=37);
        comment =  "Operand Stack: ... -> ..., value from local variable #" + (opcode - 34);
        mmemonic = "fload_" + (opcode - 34) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

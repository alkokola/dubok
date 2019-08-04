package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class iload_n  extends Instruction {

    public iload_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert (opcode >= 26) && (opcode <=29);
        length = 1;
        comment =  "Operand Stack: ... -> ..., value from local variable #" + (opcode - 26);
        mmemonic = "iload_" + (opcode - 26) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

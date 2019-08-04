package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class dload_n extends Instruction {

    public dload_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert (opcode >= 38) && (opcode <=41);
        comment =  "Operand Stack: ... -> ..., value from local variables #" + (opcode - 38)
        + " and #" + (opcode - 37);
        mmemonic = "dload_" + (opcode - 38) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

}

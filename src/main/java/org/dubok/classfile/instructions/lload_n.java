package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class lload_n extends Instruction {

    public lload_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert (opcode >= 30) && (opcode <=33);
        comment =  "Operand Stack: ... -> ..., value from local variable #" + (opcode - 30);
        mmemonic = "lload_" + (opcode - 30) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

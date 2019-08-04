package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class dstore_n extends Instruction {

    public dstore_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode >= 71 || opcode <= 74;
        comment =  "Operand Stack: ..., value -> ...";
        mmemonic = "dstore_" + (opcode - 71) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

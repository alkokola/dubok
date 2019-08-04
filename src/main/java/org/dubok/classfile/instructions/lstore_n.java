package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class lstore_n  extends Instruction {

    public lstore_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode >= 63 || opcode <= 66;
        comment =  "Operand Stack: ..., value -> ...";
        mmemonic = "lstore_" + (opcode - 63) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

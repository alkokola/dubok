package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class istore_n  extends Instruction {

    public istore_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode >= 59 || opcode <= 62;
        comment =  "Operand Stack: ..., value -> ...";
        mmemonic = "istore_" + (opcode - 59) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class fstore_n extends Instruction {

    public fstore_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode >= 67 || opcode <= 70;
        comment =  "Operand Stack: ..., value -> ...";
        mmemonic = "fstore_" + (opcode - 67) ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}


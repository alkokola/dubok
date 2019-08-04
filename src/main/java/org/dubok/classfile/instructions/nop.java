package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class nop  extends Instruction {

    public nop(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 0;
        comment =  "Operand Stack: No change";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}
package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class pop2 extends Instruction {

    public pop2(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 88;
        comment =  "..., value1, value2 -> ...";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}
package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class pop extends Instruction {

    public pop(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 87;
        comment =  "..., value -> ...";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}
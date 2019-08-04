package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class land extends Instruction {
    public land(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 127;
        comment =  "Operand Stack: ..., value1, value2 -> ..., result";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}


package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class drem extends Instruction {

    public drem(int offset, int opcode, Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 115;
        comment =  "Operand Stack: ..., value1, value2 -> ..., result";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class dneg extends Instruction {
    public dneg(int offset, int opcode, Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 119;
        comment =  "Operand Stack: ..., value -> ..., result";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

}

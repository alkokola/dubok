package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class dup  extends Instruction {
    public dup(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 89;
        comment =  "Operand Stack: ..., value -> ..., value, value";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

}

package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
public class dup2_x1 extends Instruction {

    public dup2_x1 (int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 93;
        comment =  "Operand Stack: ..., value3, value2, value1 -> ..., value2, value1, value3, value2, value1";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

}

package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class f2d extends Instruction {
    public f2d(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 141;
        comment =  "Operand Stack: ..., value -> ..., result";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

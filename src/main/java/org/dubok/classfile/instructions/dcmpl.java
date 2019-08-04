package org.dubok.classfile.instructions;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class dcmpl extends Instruction {
    public dcmpl(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 151;
        length = 1;
        comment =  "Operand Stack: ..., value1, value2 -> ..., result";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

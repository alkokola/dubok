package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class lreturn extends Instruction{
    public lreturn(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 173;
        comment =  "Operand Stack: ..., value -> [empty]";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}

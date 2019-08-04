package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class return_ extends Instruction {
    public return_(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 177;
        comment = "Operand Stack: ... -> [empty]";
        mmemonic = "return" ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
}


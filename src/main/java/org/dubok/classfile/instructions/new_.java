package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class new_ extends Instruction {
    public final int index;
    String extraComment;
    public new_(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 187;
        index = dataInput.readUnsignedShort();
        length = 3;
        comment = "Operand Stack: ... -> objectref";
        extraComment = "objectref is taken from local variable #" + index;
        mmemonic = "new" ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }

}


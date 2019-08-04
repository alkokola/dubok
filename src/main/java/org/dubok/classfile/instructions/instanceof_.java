package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class instanceof_ extends Instruction{

    public final int index;
    public String extraComment;

    public instanceof_(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 193;
        index = dataInput.readUnsignedShort();
        length = 3;
        comment = "Operand Stack: ..., objectref -> ..., result";
        extraComment = "type name is taken from constant pool at index #" + index ;
        mmemonic = "instanceof" ;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
        constantPool[index].print(printStream, indent + getIndent() + "// ");
    }
}


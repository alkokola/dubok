package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class invokedynamic extends Instruction{

    public final int index;
    public String extraComment;

    public invokedynamic(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 186;
        index = dataInput.readUnsignedShort();
        dataInput.readUnsignedShort(); // skip two bytes

        length = 5;
        comment = "Operand Stack: ..., [arg1, [arg2 ...]] -> ...";
        extraComment = "Type name is taken from constant pool at index #" + index ;
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


package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public final class astore_n extends Instruction {
    String extraComment;
    public astore_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert (opcode >= 75) && (opcode <= 78);
        comment = "Operand Stack: ..., objectref -> ...";
        extraComment = "objectref is stored into local variable #" + (opcode - 75);
        mmemonic = "astore_" +  (opcode - 75);
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}
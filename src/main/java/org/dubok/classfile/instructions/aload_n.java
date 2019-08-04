package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;


public final class aload_n extends Instruction {
    String extraComment;
    public aload_n(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert (opcode >= 42) && (opcode <= 45);
        comment = "Operand Stack: ... -> objectref";
        extraComment = "objectref is taken from local variable #" + (opcode - 42);
        mmemonic = "aload_" +  (opcode - 42);
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }
}
package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class wide extends Instruction {
    public final int opcode_w;
    public final int index;
    public int increment;
    public String extraComment;
    public wide(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 196;
        length = 4;
        comment = "Operand Stack: Same as modified instruction";
        opcode_w = dataInput.readUnsignedByte();
        index = dataInput.readShort();
        // where <opcode> is one of
        // iload, fload, aload, lload, dload, istore,
        // fstore, astore, lstore, dstore, or ret

        switch (opcode_w) {
            case 21: // iload
            case 23: // fload
            case 25: // aload
            case 22: // lload
            case 24: // dload
            case 54: // istore
            case 56: // fstore
            case 58: // astore
            case 55: // lstore
            case 57: // dstore
            case 169: // ret

                extraComment = "opcode = " + opcode_w + ", local variable #" + index;
                break;
            case 132: // iinc
                increment = dataInput.readShort();
                length = 6;
                extraComment = "opcode iinc, local variable #" + index + ", incr = " + increment;

            default:

        }

        //extraComment = "objectref is stored into local variable #" + index;
        mmemonic = "wide";
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }


}
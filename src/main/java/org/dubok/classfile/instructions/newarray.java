package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class newarray extends Instruction {
    public final int atype;
    public static final int T_BOOLEAN = 4;
    public static final int T_CHAR = 5;
    public static final int T_FLOAT = 6;
    public static final int T_BYTE = 7;
    public static final int T_DOUBLE = 8;
    public static final int T_SHORT = 9;
    public static final int T_INT = 10;
    public static final int T_LONG = 11;

    String extraComment;
    public newarray(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset,opcode,constantPool,instructions);
        assert opcode == 188;
        length = 2;
        comment = "Operand Stack: ...,  count1 -> ..., arrayref";
        atype = dataInput.readUnsignedByte();
        String type;
        switch (atype) {
            case T_BOOLEAN: type = "T_BOOLEAN"; break;
            case T_CHAR: type = "T_CHAR"; break;
            case T_FLOAT: type = "T_FLOAT"; break;
            case T_BYTE: type = "T_BYTE"; break;
            case T_DOUBLE: type = "T_DOUBLE"; break;
            case T_SHORT: type = "T_SHORT"; break;
            case T_INT: type = "T_INT"; break;
            case T_LONG: type = "T_LONG"; break;
            default: throw new ClassFormatError("Invalid array type: " + atype );
        }
        extraComment = "array type is #" + type;
        maxMnemonicLength = mmemonic.length();
        instructions.add(this);
    }
    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        printStream.println(indent + getIndent() + "// " + extraComment);
    }

}
